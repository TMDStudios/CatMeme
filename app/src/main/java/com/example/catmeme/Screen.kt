package com.example.catmeme

sealed class Screen(val route: String){
    object ImageScreen: Screen("image_screen")
    object MemeScreen: Screen("meme_screen")

    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}