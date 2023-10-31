package com.example.threadapp.screens

import AuthBottomLabel
import CustomButton
import CustomTextFiled
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.threadapp.navigation.Routes
import io.grpc.okhttp.internal.Util

@Composable
fun LoginScreen(navHostController: NavHostController) {

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }

    val modifier= Modifier
        .fillMaxWidth()
        .padding(20.dp, 0.dp)
    Box(modifier = Modifier.background(color = Color.Black)) {
        Column(modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Welcome back,", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif, textAlign = TextAlign.Start), modifier =modifier)
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextFiled(textFiled =email, callBack = {
                email=it
            },"Email",modifier,false)
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextFiled(textFiled =password, callBack = {
                password=it
            },"Password",modifier,true,focusRequester)
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton({
                com.example.threadapp.util.Util.goTo(navHostController,Routes.Register.route)

            },"Login",modifier)
            Spacer(modifier = Modifier.height(20.dp))
            AuthBottomLabel("Don't have an account? ","Register", onTap ={
                com.example.threadapp.util.Util.goTo(navHostController,Routes.Register.route)
            })
        }
    }

}
