package com.apollo.nari.playwithapollo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import kotlinx.android.synthetic.main.activity_repos.*

class ReposActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)


        createView()
    }

    private fun createView() {
        var layoutManager = LinearLayoutManager(this)
//        var adapter = ReposAdapter()
//
//        repo_rv.layoutManager = layoutManager
//        repo_rv.adapter = adapter
    }
}