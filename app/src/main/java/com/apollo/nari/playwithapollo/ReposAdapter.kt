package com.apollo.nari.playwithapollo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apollo.nari.playwithapollo.databinding.RepoListItemViewBinding

class ReposAdapter(val edges: List<FindReposByName.Edge>) : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {
    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.onBindView(edges.get(position).node()!!)
    }

    override fun getItemCount(): Int {
        return edges.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val binding = RepoListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReposViewHolder(binding)
    }

    class ReposViewHolder constructor(val binding: RepoListItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBindView(node: FindReposByName.Node) {
            binding.repoInfo.text = "Repo name : " + node?.name() +
                    "\n Description : " + node?.description() +
                    "\n Fork count : " + node?.forkCount() +
                    "\n Url : " + node?.url()
        }
    }

}