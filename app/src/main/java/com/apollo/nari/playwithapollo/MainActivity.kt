package com.apollo.nari.playwithapollo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.apollo.nari.playwithapollo.databinding.ActivityMainBinding
import com.apollo.nari.playwithapollo.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)

        viewModel.edges.observe(this, Observer { edges -> createView(edges!!)})
        find_button.setOnClickListener{
            viewModel.loadRepos()
        }
    }

    private fun createView(edges: List<FindReposByName.Edge>) {
        var layoutManager = LinearLayoutManager(this)
        binding.repoRv.layoutManager = layoutManager
        binding.repoRv.adapter = ReposAdapter(edges)
        binding.findButton.visibility = View.GONE
    }
}
