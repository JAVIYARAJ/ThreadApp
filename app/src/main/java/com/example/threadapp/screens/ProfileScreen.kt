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
import com.example.threadapp.R
import com.example.threadapp.model.UserModel
import com.example.threadapp.navigation.Routes
import com.example.threadapp.viewmodels.AuthViewModel



@Composable
fun ProfileScreen(controller: NavHostController) {
    val authViewModel = remember { AuthViewModel() }

    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val profileData by authViewModel.profileData.observeAsState()
    val userProfileData = remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            controller.navigate(Routes.Login.route) {
                popUpTo(controller.graph.startDestinationId)
            }
        }
    }

    authViewModel.getProfileData()

    LaunchedEffect(profileData) {
        userProfileData.value = profileData
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (profileCard, bio,profileButtons) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(profileCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val (name, username, threadCard, userImage, verifiedIcon) = createRefs()

                Text(
                    text = userProfileData.value?.name ?: "",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    ),
                    modifier = Modifier.constrainAs(name) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                )

                Text(
                    text = userProfileData.value?.username ?: "",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                    ),
                    modifier = Modifier
                        .constrainAs(username) {
                            start.linkTo(parent.start)
                            top.linkTo(name.bottom)
                            top.linkTo(name.bottom)
                            top.linkTo(name.bottom)
                            top.linkTo(name.bottom)
                        }
                        .padding(top = 10.dp)
                )

                Card(
                    modifier = Modifier
                        .constrainAs(threadCard) {
                            start.linkTo(username.end)
                            top.linkTo(name.bottom)
                        }
                        .padding(top = 10.dp, start = 10.dp)
                        .height(25.dp)
                        .width(90.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text = "threads.net",
                        style = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_people_icon),
                    contentDescription = "user_image",
                    modifier = Modifier
                        .constrainAs(userImage) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .height(80.dp)
                        .width(80.dp)
                        .clip(CircleShape)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_verified_icon),
                    contentDescription = "verified",
                    modifier = Modifier
                        .constrainAs(verifiedIcon) {
                            start.linkTo(userImage.start)
                            bottom.linkTo(userImage.bottom)
                        }
                        .height(20.dp)
                        .width(20.dp)
                        .clip(CircleShape)
                )
            }
        }

        Text(
            text = userProfileData.value?.bio ?: "",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
            ),
            modifier = Modifier
                .constrainAs(bio) {
                    start.linkTo(parent.start)
                    top.linkTo(profileCard.bottom)
                }
                .padding(top = 10.dp, start = 15.dp)
                .width(200.dp)
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.constrainAs(profileButtons){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(bio.bottom)
        }.padding(start = 15.dp, top = 20.dp, end = 10.dp)) {
            ProfileButton(title = "Edit Profile", onTap = { }, modifier = Modifier
               .weight(0.5f,false).padding(end = 10.dp) )
            ProfileButton(title = "Share Profile", onTap = { }, modifier = Modifier
               .weight(0.5f,false).padding(start = 10.dp) )
        }



    }

}
