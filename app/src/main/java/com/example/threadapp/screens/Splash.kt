package com.example.threadapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import com.example.threadapp.R
import com.example.threadapp.navigation.Routes
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SplashScreen(controller: NavHostController) {
    
    val constraints= ConstraintSet{
        val imageRef=createRefFor("image")
        val text=createRefFor("text")

        constrain(imageRef){
            top.linkTo(parent.top)
            bottom.linkTo(text.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text){
            top.linkTo(imageRef.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(constraints,modifier = Modifier.fillMaxSize()) {
        val (image) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ic_logo_icon),
            contentDescription = "app_logo",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .layoutId("image"),
        )
        
        Text(text = "Created by javiya raj",Modifier.layoutId("text"))
        
    }

    LaunchedEffect(true) {
        delay(3000)
        controller.navigate(Routes.BottomNav.route)
    }

}