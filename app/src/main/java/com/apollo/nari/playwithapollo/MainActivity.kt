package com.apollo.nari.playwithapollo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
                            var node = repositories.edges()?.get(0)?.node()
                            contents.text = "Repo name : " + node?.name()
                                    "\n Description : " + node?.description() +
                                    "\n Fork count : " + node?.forkCount() +
                                    "\n Url : " + node?.url()
//                            description_text_view.text = String.format(getString(R.string.description_text),
//                                    response.data()?.repository()?.description())
//                            forks_text_view.text = String.format(getString(R.string.fork_count_text),
//                                    response.data()?.repository()?.forkCount().toString())
//                            url_text_view.text = String.format(getString(R.string.url_count_text),
//                                    response.data()?.repository()?.url().toString())
                        })
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
