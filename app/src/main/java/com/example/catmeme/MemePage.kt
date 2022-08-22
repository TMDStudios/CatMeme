package com.example.catmeme

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage

@Composable
fun MemePage(imgUrl: String?){
    var imgLink = Uri.parse("https://cdn2.thecatapi.com/images/$imgUrl").toString()

    SubcomposeAsyncImage(
        model=imgLink,
        loading = {
            CircularProgressIndicator()
        },
        error = {
            CircularProgressIndicator()
        },
        contentDescription = "cat1",
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}