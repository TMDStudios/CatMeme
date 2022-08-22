package com.example.catmeme

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.ImageScreen.route) {
        composable(route = Screen.ImageScreen.route){
            ImagePage(navController = navController)
        }
        composable(route = Screen.MemeScreen.route + "/{imgUrl}",
            arguments = listOf(navArgument("imgUrl"){
            type = NavType.StringType
            defaultValue = Uri.parse("android.resource://com.example.catmeme/"+R.drawable.cat1).toString()
            nullable = true
        })) { entry ->
            MemePage(imgUrl = entry.arguments?.getString("imgUrl"))
        }
    }
}