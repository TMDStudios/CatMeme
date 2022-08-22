package com.example.catmeme

import android.net.Uri
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ImagePage(navController: NavController){
    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
    // Set default img link to our placeholder cat image
    var imgLink by remember { mutableStateOf(
        Uri.parse("android.resource://com.example.catmeme/"+R.drawable.cat1).toString()) }

    Card(elevation = 6.dp, modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(30.dp))
    ) {
        BoxWithConstraints() {
            ConstraintLayout(portraitConstraints(margin=16.dp)){

                Text(text="", modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center))

                Image(
                    painter = painterResource(id = R.drawable.cat1),
                    alpha = .25f,
                    contentDescription = "cat1",
                    modifier = Modifier
                        .size(320.dp)
                        .clip(CircleShape)
                        .layoutId("imageBG"),
                    contentScale = ContentScale.Crop)

                // Display Image with Coil
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
                        .size(320.dp)
                        .clip(CircleShape)
                        .layoutId("image"),
                    contentScale = ContentScale.Crop
                )

                Text(text="Current Image Link:", Modifier.layoutId("labelText"))
                Text(text=imgLink,
                    fontWeight = FontWeight.Bold, modifier = Modifier.layoutId("urlText"))

                Button(onClick = {
                    imgLink = "Getting Image"
                    apiInterface?.getImg()?.enqueue(object: Callback<CatImg> {
                        override fun onResponse(call: Call<CatImg>, response: Response<CatImg>) {
                            try{
                                imgLink = response.body()!![0].url
                            }catch(e: Exception){
                                Log.d("MAIN", "ISSUE: $e")
                            }
                        }

                        override fun onFailure(call: Call<CatImg>, t: Throwable) {
                            Log.d("MAIN", "Unable to get data")
                        }

                    })},
                    modifier = Modifier.layoutId("getImgBtn")) {
                    Text(text = "Get Image")
                }
                Button(onClick = {
                    Log.d("ImagePage", "LINK: $imgLink")
                    val imageId = imgLink.split("/")
                    navController.navigate(Screen.MemeScreen.withArgs(imageId[imageId.size-1]))
                },
                    modifier = Modifier.layoutId("selectImgBtn")) {
                    Text(text = "Select Image")
                }

            }
        }
    }
}

private fun portraitConstraints(margin:Dp): ConstraintSet{
    return ConstraintSet{
        val imageBG = createRefFor("imageBG")
        val image = createRefFor("image")
        val labelText = createRefFor("labelText")
        val urlText = createRefFor("urlText")
        val getImgBtn = createRefFor("getImgBtn")
        val selectImgBtn = createRefFor("selectImgBtn")
        val guideline = createGuidelineFromTop(0.15f)

        constrain(imageBG){
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(image){
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(labelText){
            top.linkTo(image.bottom, margin=margin)
            start.linkTo(image.start)
        }
        constrain(urlText){
            top.linkTo(labelText.bottom)
            start.linkTo(image.start)
        }
        constrain(getImgBtn){
            top.linkTo(urlText.bottom, margin=margin)
            start.linkTo(parent.start)
            end.linkTo(selectImgBtn.start)
            width = Dimension.value(140.dp)
        }
        constrain(selectImgBtn){
            top.linkTo(urlText.bottom, margin=margin)
            start.linkTo(getImgBtn.end)
            end.linkTo(parent.end)
            width = Dimension.value(140.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePagePreview(){

}