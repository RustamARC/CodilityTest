package com.rustam.newsapp.screen

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rustam.network.model.Article
import com.rustam.newsapp.ui.theme.Typography
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NewsCard(article: Article) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = article.title, style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 25.sp,
                    fontFamily = FontFamily(Typeface.DEFAULT),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                )
            )
            LoadNewsImage(url = article.urlToImage)
            Text(
                text = article.content ?: "", style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Typeface.DEFAULT),
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Author : ${article.author ?: ""}", style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Typeface.DEFAULT),
                    fontWeight = FontWeight(400),
                    color = Color.Gray,
                    textAlign = TextAlign.Left,
                )
            )
            Text(
                text = getFormattedPublishedDate(article.publishedAt), style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Typeface.DEFAULT),
                    fontWeight = FontWeight(400),
                    color = Color.Gray,
                    textAlign = TextAlign.Justify,
                )
            )

            Log.e("Article", article.toString())
        }
    }
}


@Composable
fun LoadNewsImage(url: String?) {
    AsyncImage(
        model = url,
        contentDescription = "News Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        //.padding(5.dp),
        contentScale = ContentScale.Crop
    )
}


fun getFormattedPublishedDate(publishedAt: String): String {
    val zonedDateTime = ZonedDateTime.parse(publishedAt)
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, HH:mm a")
    return "Published on : ${zonedDateTime.format(formatter)}"
}