package com.rustam.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.rustam.network.model.Article
import com.rustam.network.model.NewsResponse
import com.rustam.newsapp.screen.NewsCard
import com.rustam.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewsActivity : ComponentActivity() {
    private val newsViewModel by viewModels<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    val response = remember {
                        mutableStateOf("Top Headlines")
                    }

                    var newsResponse: Deferred<NewsResponse>? = null
                    LaunchedEffect(key1 = response.value) {
                        newsResponse = lifecycleScope.async(Dispatchers.IO) {
                            newsViewModel.fetchNews(
                                query = "jetpack compose",
                                apiKey = "6b33620784684ceaa5874ad285bf6d5a"
                            )

                        }

                    }
                    val newsList = remember { mutableStateOf(listOf<Article>()) }
                    LaunchedEffect(key1 = response.value) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            val result = newsResponse?.await()
                            if (result?.status == "ok") {
                                if (result.totalResults > 0) {
                                    newsList.value = result.articles
                                }
                            }
                        }
                    }
                    if (newsList.value.isNotEmpty())
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White), contentPadding = PaddingValues(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(newsList.value) { article ->
                                NewsCard(article = article)
                            }
                        }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {

    }
}