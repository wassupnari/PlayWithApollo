package com.apollo.nari.playwithapollo

import android.app.Application
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class MyApplication: Application() {
    private val BASE_URL = "https://api.github.com/graphql"
    lateinit var apolloClient: ApolloClient

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        setupApolloClient()
    }

    fun setupApolloClient() {
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
        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttp)
                .build()
    }
}