package com.example.git_repo_app


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_repo_app.response.Repos
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File


class MainActivity : Activity() {

    lateinit var repoList: RecyclerView
    var haveStorage = false
    var was = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            was = savedInstanceState.getBoolean("was")
            haveStorage = savedInstanceState.getBoolean("haveStorage")
        }
        setContentView(R.layout.activity_main)
        repoList = findViewById(R.id.repoList)
        repoList.layoutManager = LinearLayoutManager(this)
        val refreshButton = findViewById<Button>(R.id.refreshButton)
        if(was && haveStorage) {
            readResultListFromFile()
        } else {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                haveStorage = true
            }
            val bundle = intent.extras
            if(checkConnection(MyApp.instance.getAppContext())) {
                val user_request = bundle?.getString("user_request")
                val user_language = bundle?.getString("language_chosen")
                requestRepos(user_request, user_language)
                refreshButton.setOnClickListener {
                    requestRepos(user_request, user_language)
                }
                refreshButton.isEnabled=true
            } else {
                AlertDialog.Builder(this).setTitle(R.string.no_internet)
                        .setMessage(R.string.no_internet_hint)
                        .setPositiveButton(R.string.ok){_, _ ->}
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                refreshButton.isEnabled=false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("was", was)
        outState.putBoolean("haveStorage", haveStorage)
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
                    .setTitle(R.string.error)
                    .setMessage(exception.message)
                    .setPositiveButton(R.string.ok) { _, _ ->}
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
                if(haveStorage) {
                    writeToFile(resultList)
                }
            }
            repoList.adapter = RepoAdapter(resultList)
            was = true
        }
    }

    private fun writeToFile(resultList: Repos) {
        val appFile = File(MyApp.instance.getAppContext().getExternalFilesDir(null),
                "git_repo_app_tmp")
        appFile.writeText(Gson().toJson(resultList))
    }

    private fun readResultListFromFile() {
        val appFile = File(MyApp.instance.getAppContext().getExternalFilesDir(null),
                "git_repo_app_tmp")
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        var resultList: Repos
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                resultList = Gson().fromJson(appFile.readText(), Repos::class.java)
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

