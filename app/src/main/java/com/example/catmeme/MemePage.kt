package com.example.catmeme

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemePage(imgUrl: String?){
    var imgLink = Uri.parse("https://cdn2.thecatapi.com/images/$imgUrl").toString()
    var memeText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showTextField by remember{ mutableStateOf(true) }

    // The number equals R.drawable.cat1
    if(imgUrl!! == "2130968578"){imgLink="android.resource://com.example.catmeme/2130968578"}

    ConstraintLayout(portraitConstraints(showTextField)){

        SubcomposeAsyncImage(
            model=imgLink,
            loading = {
                CircularProgressIndicator()
            },
            error = {
                CircularProgressIndicator()
            },
            contentDescription = "cat image",
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("image"),
            contentScale = ContentScale.Crop
        )

        Text(text=memeText,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .background(Color.White)
                .padding(horizontal = 12.dp)
                .layoutId("memeText")
                .clickable { showTextField = true },
            fontSize = 24.sp)

        if(showTextField){
            TextField(
                value = memeText,
                onValueChange = { memeText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .layoutId("memeTextField"),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        showTextField = false
                    })
            )
        }

    }
}

private fun portraitConstraints(textFieldVisible: Boolean): ConstraintSet {
    return ConstraintSet{
        val image = createRefFor("image")
        val memeText = createRefFor("memeText")
        val memeTextField = createRefFor("memeTextField")

        constrain(image){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(memeText){
            if(textFieldVisible){
                bottom.linkTo(memeTextField.top)
            }else{
                bottom.linkTo(parent.bottom, margin = 24.dp)
            }
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(memeTextField){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}