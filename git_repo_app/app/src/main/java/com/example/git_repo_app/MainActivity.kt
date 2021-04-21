package com.example.git_repo_app


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_repo_app.api.RepoTaker
import com.example.git_repo_app.response.Repos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : Activity() {

    lateinit var repoList: RecyclerView
    private val taker = RepoTaker()

    private val callback = object: Callback<Repos> {
        override  fun onFailure(call: Call<Repos>?, t:Throwable?) {
            Log.e("MainActivity", "Can't call Github API {${t?.message}}")
        }
        override  fun onResponse(call: Call<Repos>?, response: Response<Repos>?){
            response?.isSuccessful.let {
                val resultList = Repos(response?.body()?.items ?: emptyList())
                repoList.adapter = RepoAdapter(resultList)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repoList = findViewById(R.id.repoList)
        repoList.layoutManager = LinearLayoutManager(this)
        val refreshButton = findViewById<Button>(R.id.refreshButton)
        if(checkConnection(MyApp.instance.getAppContext())) {
            println("network is available")
            taker.getRepos(callback)
            refreshButton.setOnClickListener {
                taker.getRepos(callback)
            }
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
}

