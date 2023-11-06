package com.example.threadapp.screens

import com.example.threadapp.widgets.ProfileButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadapp.R
import com.example.threadapp.model.UserModel
import com.example.threadapp.navigation.Routes
import com.example.threadapp.viewmodels.AuthViewModel
import com.example.threadapp.widgets.ReusableProfileScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(controller: NavHostController) {
    val authViewModel = remember { AuthViewModel() }

    val profileData by authViewModel.profileData.observeAsState()

    authViewModel.getProfileData(FirebaseAuth.getInstance().currentUser!!.uid)


    if (profileData != null) {
        ReusableProfileScreen(userProfileData = profileData!!)
    }

}
