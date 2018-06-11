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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = setupApolloClient()
        find_button.setOnClickListener{v -> findQuery()}

    }

    private fun findQuery() {
        Log.d("APOLLO", "find query called")
        client.query(FindQuery    //From the auto generated class
                .builder()
                .name("butterknife") //Passing required arguments
                .owner("jakewharton") //Passing required arguments
                .build())
                .enqueue(object : ApolloCall.Callback<FindQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
//                        Log.info(e.message.toString())
                        Log.d("APOLLO", "find query failed")
                    }
                    override fun onResponse(response: Response<FindQuery.Data>) {
//                        Log.info(" " + response.data()?.repository())
                        runOnUiThread({
                            Log.d("APOLLO", "find query success")
                            //                            progress_bar.visibility = View.GONE
                            contents.text = "Repo : " + response.data()?.repository()?.name()
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
                            , "Bearer " + "token")
                    chain.proceed(builder.build())
                })
                .build()
        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttp)
                .build()
    }
}
