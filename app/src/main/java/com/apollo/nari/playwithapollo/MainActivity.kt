package com.apollo.nari.playwithapollo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.github.com/graphql"
    private lateinit var client: ApolloClient
    private lateinit var repositories: FindReposByName.Repositories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = setupApolloClient()
        find_button.setOnClickListener{v -> findQuery()}

    }

    private fun findQuery() {
        Log.d("APOLLO", "find query called")
        client.query(FindReposByName    //From the auto generated class
                .builder()
                .owner(repo_name.text.toString()) //Passing required arguments
                .build())
                .enqueue(object : ApolloCall.Callback<FindReposByName.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("APOLLO", "find query failed")
                    }
                    override fun onResponse(response: Response<FindReposByName.Data>) {
                        runOnUiThread({
                            Log.d("APOLLO", "find query success")
                            //                            progress_bar.visibility = View.GONE
                            repositories = response.data()?.repositoryOwner()?.repositories()!!
                            createView(repositories.edges()!!)
                        })
                    }
                })
    }

    private fun createView(edges: List<FindReposByName.Edge>) {
        var layoutManager = LinearLayoutManager(this)
        repo_rv.layoutManager = layoutManager
        repo_rv.adapter = ReposAdapter(edges)
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
