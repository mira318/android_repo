package com.example.git_repo_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.git_repo_app.R.layout.repo_item
import com.example.git_repo_app.response.Item
import com.example.git_repo_app.response.Repos
import com.squareup.picasso.Picasso

class RepoAdapter(private val repos: Repos): RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindRepo(repo: Item) {
            val repoName = view.findViewById<TextView>(R.id.repoName)
            repoName.text = repo.name

            val repoDescription = view.findViewById<TextView>(R.id.repoDescription)
            repoDescription.text = repo.description

            val username = view.findViewById<TextView>(R.id.creatorName)
            username.text = repo.owner.login

            val icon = view.findViewById<ImageView>(R.id.icon)
            Picasso.get()
                .load(repo.owner.avatar_url)
                .into(icon)
        }
    }

    override fun getItemCount(): Int = repos.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        holder.bindRepo(repos.items[index])
    }

}

