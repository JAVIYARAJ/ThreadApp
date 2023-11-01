package com.example.threadapp.screens

import ProfileButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import com.example.threadapp.R
import com.example.threadapp.model.UserModel
import com.example.threadapp.navigation.Routes
import com.example.threadapp.viewmodels.AuthViewModel

@Composable
fun ProfileScreen(controller: NavHostController) {

    val authViewModel = AuthViewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val profileData by authViewModel.profileData.observeAsState()
    var userProfileData by remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            controller.navigate(Routes.Login.route) {
                popUpTo(controller.graph.startDestinationId)
            }
        }
    }

    authViewModel.getProfileData()

    LaunchedEffect(profileData) {
        userProfileData = profileData
    }


    val parentConstraintSet = ConstraintSet {
        val profileCard = createRefFor("profile_card")

        val bio = createRefFor("bio")
        val editBtn = createRefFor("edit_profile_btn")
        val shareBtn = createRefFor("share_profile_btn")
        val tabLayout = createRefFor("tab_layout")

        constrain(editBtn) {
            start.linkTo(parent.start)
            top.linkTo(bio.bottom)
        }

        constrain(bio) {
            start.linkTo(parent.start)
            top.linkTo(profileCard.bottom)
        }

        constrain(shareBtn) {
            top.linkTo(bio.bottom)
            end.linkTo(parent.end)
        }

        constrain(profileCard) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }

        constrain(tabLayout) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(editBtn.bottom)
        }

    }

    ConstraintLayout(parentConstraintSet) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("profile_card")
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
        ) {

            val constraints = ConstraintSet {

                val name = createRefFor("name")
                val username = createRefFor("username")
                val threadCard = createRefFor("thread_card")
                val userImage = createRefFor("user_image")
                val bio = createRefFor("bio")
                val verifiedIcon = createRefFor("verified_icon")
                val editBtn = createRefFor("edit_profile_btn")
                val shareBtn = createRefFor("share_profile_btn")

                constrain(name) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }

                constrain(username) {
                    start.linkTo(parent.start)
                    top.linkTo(name.bottom)
                }

                constrain(threadCard) {
                    start.linkTo(username.end)
                    top.linkTo(name.bottom)
                }

                constrain(userImage) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }

                constrain(shareBtn) {
                    top.linkTo(bio.bottom)
                    end.linkTo(parent.end)
                }

                constrain(verifiedIcon) {
                    start.linkTo(userImage.start)
                    bottom.linkTo(userImage.bottom)
                }

            }

            ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = userProfileData?.name ?: "",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    ),
                    modifier = Modifier.layoutId("name")
                )

                Text(
                    text = userProfileData?.username ?: "",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                    ),
                    modifier = Modifier
                        .layoutId("username")
                        .padding(top = 10.dp)
                )

                Card(
                    modifier = Modifier
                        .layoutId("thread_card")
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
                        .height(100.dp)
                        .width(100.dp)
                        .layoutId("user_image")
                        .clip(
                            CircleShape
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_verified_icon),
                    contentDescription = "verified",
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .layoutId("verified_icon")
                        .clip(
                            CircleShape
                        )
                )


            }
        }

        Text(
            text = userProfileData?.bio ?: "",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
            ),
            modifier = Modifier
                .layoutId("bio")
                .padding(top = 10.dp, start = 15.dp)
                .width(200.dp)
        )

        ProfileButton("Edit Profile", "edit_profile_btn", onTap = {

        }, modifier = Modifier.padding(start = 15.dp, top = 10.dp))

        ProfileButton("Share Profile", "share_profile_btn", onTap = {
        }, modifier = Modifier.padding(end = 15.dp, top = 10.dp))
        /*
        Row(modifier = Modifier.layoutId("tab_layout").height(50.dp).padding(top = 10.dp),) {
            Text(text = "Tab1", textAlign = TextAlign.Center, style = TextStyle(color = Color.White,),modifier = Modifier
                .fillMaxHeight()
                .weight(1f) // Equal width for all children
                .background(Color.Black))
            Text(text = "Tab2",modifier = Modifier
                .fillMaxHeight()
                .weight(1f) // Equal width for all children
                .background(Color.Green))
            Text(text = "Tab3",modifier = Modifier
                .fillMaxHeight()
                .weight(1f) // Equal width for all children
                .background(Color.Blue))
        }

         */

    }


}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProfileCard() {


}
