package com.example.git_repo_app


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_repo_app.api.RepoTaker
import kotlinx.coroutines.*


class MainActivity : Activity() {

    lateinit var repoList: RecyclerView
    lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repoList = findViewById(R.id.repoList)
        repoList.layoutManager = LinearLayoutManager(this)
        if(checkConnection(MyApp.instance.getAppContext())) {
            println("network is available")
            tryRepos()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection")
                    .setPositiveButton(android.R.string.ok){_, _ ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()

        }
    }

    override fun onStop() {
        scope.cancel()
        super.onStop()
    }

    private fun tryRepos() {
        println("created error handler")
        scope = CoroutineScope(Dispatchers.Main)
        scope.launch(Dispatchers.Main) {
            println("launched")
            val resultList = withContext(Dispatchers.IO){
                RepoTaker().getRepos()
            }
            println("did async task in launcher")
            repoList.adapter = RepoAdapter(resultList)
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

