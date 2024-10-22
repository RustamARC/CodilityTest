package com.rustam.newsapp

import androidx.lifecycle.ViewModel
import com.rustam.network.api.NewsApi
import com.rustam.network.model.NewsResponse

class NewsViewModel : ViewModel() {

    suspend fun fetchNews(query:String,apiKey:String):NewsResponse {
       return NewsApi.getInstance().fetchNews(query = query, apiKey = apiKey)
    }
}