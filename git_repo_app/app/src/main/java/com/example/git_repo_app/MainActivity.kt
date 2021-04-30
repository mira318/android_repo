package com.example.git_repo_app


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_repo_app.response.Repos
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : Activity() {

    lateinit var repoList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        repoList = findViewById(R.id.repoList)
        repoList.layoutManager = LinearLayoutManager(this)
        val refreshButton = findViewById<Button>(R.id.refreshButton)

        if(checkConnection(MyApp.instance.getAppContext())) {
            val user_request = bundle?.getString("user_request")
            val user_language = bundle?.getString("language_chosen")
            println("user_language=$user_language")
            requestRepos(user_request, user_language)
            refreshButton.setOnClickListener {
                requestRepos(user_request, user_language)
            }
            refreshButton.isActivated=true
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection")
                    .setPositiveButton(android.R.string.ok){_, _ ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            refreshButton.isEnabled=true
        }
    }

    private fun checkConnection(context: Context): Boolean {
        val manager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val isActive = manager.activeNetwork
        val capabilities = manager.getNetworkCapabilities(isActive)
        return capabilities != null && capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun requestRepos(user_request: String?, user_language: String?) {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) { _, _ ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        lateinit var resultList: Repos
        var urlString = getUrlString(user_request, user_language)

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val request = Request.Builder().get().url(urlString).build()

                val response = client.newCall(request).execute()
                resultList = Gson().fromJson(response.body()?.string(), Repos::class.java)
            }
            repoList.adapter = RepoAdapter(resultList)
        }
    }

    private fun getUrlString(user_request: String?, user_language: String?): String {
        var languageStr = user_language
        if(user_language == "Null") {
            languageStr=""
        }
        if(user_language == "Cplusplus") {
            languageStr="C++"
        }
        var addWords = ""
        val baseString = "https://api.github.com/search/repositories?q="
        val endString = "+language:${languageStr}&sort=stars&order=desc&per_page=100"
        var urlString: String

        if (user_request != null && user_request.isNotEmpty()) {
            var addingString = user_request.split(
                    " ", "?", ",", "\t", "\n", "!", ".", "\"", ";", ":", "+", "'", "*", "&")
            for(str in addingString) {
                str.replace(",\\|!|/|?|\t|\n|;|\"|:|+|'|*|&", "")
                println("str=$str")
                if(str.isNotEmpty()){
                    if(addWords.isEmpty()){
                        addWords = str
                    } else {
                        addWords += "+$str"
                    }
                }
            }
        }
        urlString = "$baseString$addWords$endString"
        return urlString
    }
}

