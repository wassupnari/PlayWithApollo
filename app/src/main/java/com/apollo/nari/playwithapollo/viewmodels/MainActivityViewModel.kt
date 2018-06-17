package com.apollo.nari.playwithapollo.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import okhttp3.OkHttpClient

class MainActivityViewModel : ObservableViewModel() {
    private val BASE_URL = "https://api.github.com/graphql"
    private lateinit var client: ApolloClient
    val edges: MutableLiveData<List<FindReposByName.Edge>> = MutableLiveData()
//    val ownerName: MutableLiveData<String> = MutableLiveData()
    val ownerName: ObservableField<String> = ObservableField()

    fun loadRepos() {
        client = setupApolloClient()
        client.query(FindReposByName    //From the auto generated class
                .builder()
                .owner("wassupnari") //Passing required arguments
                .build())
                .enqueue(object : ApolloCall.Callback<FindReposByName.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("APOLLO", "find query failed")
                    }
                    override fun onResponse(response: Response<FindReposByName.Data>) {
                        edges.postValue(response.data()?.repositoryOwner()?.repositories()!!.edges()!!)
                    }
                })
    }

    private fun setupApolloClient(): ApolloClient {
        val okHttp = OkHttpClient
                .Builder()
                .addInterceptor({ chain ->
                    val original = chain.request()
                    val builder = original.newBuilder().method(original.method(),
                            original.body())
                    builder.addHeader("Authorization"
                            , "Bearer " + "73aa9cf9d63815ed4003e486a87d0ca42ad12559")
                    chain.proceed(builder.build())
                })
                .build()
        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttp)
                .build()
    }
}