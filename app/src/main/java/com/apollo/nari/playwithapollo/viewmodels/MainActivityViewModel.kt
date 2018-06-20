package com.apollo.nari.playwithapollo.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.apollo.nari.playwithapollo.MyApplication
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

class MainActivityViewModel : ObservableViewModel() {


    var dataFetched = ObservableBoolean(false)
    val edges: MutableLiveData<List<FindReposByName.Edge>> = MutableLiveData()
    val ownerName: ObservableField<String> = ObservableField()

    fun loadRepos() {
        MyApplication.instance.apolloClient.query(FindReposByName    //From the auto generated class
                .builder()
                .owner("wassupnari") //Passing required arguments
                .build())
                .enqueue(object : ApolloCall.Callback<FindReposByName.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("APOLLO", "find query failed")
                    }

                    override fun onResponse(response: Response<FindReposByName.Data>) {
                        Log.d("APOLLO", "find query success")
                        dataFetched.set(true)
                        edges.postValue(response.data()?.repositoryOwner()?.repositories()!!.edges()!!)
                    }
                })
    }
}