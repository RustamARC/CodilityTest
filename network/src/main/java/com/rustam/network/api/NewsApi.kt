package com.rustam.network.api

import com.rustam.network.model.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NewsApi {

    @GET(Constant.everything)
    suspend fun fetchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    companion object {
        private var newsApi: NewsApi? = null
        fun getInstance(): NewsApi {
            if (newsApi == null) {
                newsApi = getRetrofitInstance()?.create(NewsApi::class.java)
            }
            return newsApi!!
        }

        private fun getRetrofitInstance(): Retrofit? {
            return Retrofit.Builder()
                .baseUrl(Constant.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        // API response interceptor
        private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        // Client
        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(1L, TimeUnit.MINUTES)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }


}