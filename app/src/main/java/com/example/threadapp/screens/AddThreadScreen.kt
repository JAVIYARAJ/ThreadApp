package com.example.threadapp.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadapp.R
import com.example.threadapp.navigation.Routes
import com.example.threadapp.util.Util
import com.example.threadapp.viewmodels.AuthViewModel

@Composable
fun AddThreadScreen(controller: NavHostController) {
    BottomSheetDesign(controller)
}

@Composable
fun BottomSheetDesign(controller: NavHostController) {

    var description by remember { mutableStateOf("") }

    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }

    val context = LocalContext.current

    val authViewModel = AuthViewModel()
    val currentUser by authViewModel.firebaseUser.observeAsState()
    val postMessage by authViewModel.errorMessage.observeAsState("")

    LaunchedEffect(postMessage) {
        if(postMessage.isNotEmpty()){
        Util.showToast(context, postMessage)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (postCard, firstRow, divider, username, verifiedIcon, descriptionBox, postImage, imageAttachmentIcon) = createRefs()

        val imageModifier = Modifier
            .fillMaxWidth()
            .constrainAs(postImage) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(descriptionBox.bottom)
            }
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
            .clip(RoundedCornerShape(20.dp))

        val imageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = {
                if (it.isNotEmpty()) {
                    selectedImages = it
                }
            })

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .constrainAs(firstRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }) {
            Image(painter = painterResource(id = R.drawable.ic_close_icon),
                contentDescription = "close_icon",
                modifier = Modifier.clickable {
                    controller.navigate(Routes.Home.route)
                })
            Text(
                text = "New Thread",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
            )

            Text(
                modifier = Modifier.clickable {
                    if(currentUser!=null){
                    authViewModel.createPost(currentUser!!.uid,description,selectedImages)
                    }
                },
                text = "Post",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Blue
                )
            )
        }

        Divider(thickness = 1.dp, modifier = Modifier.constrainAs(divider) {
            start.linkTo(parent.start)
            top.linkTo(firstRow.bottom)
        })

        Image(painter = painterResource(id = R.drawable.ic_people_icon),
            contentDescription = "user_image",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(start = 10.dp, top = 20.dp)
                .clip(CircleShape)
                .constrainAs(postCard) {
                    start.linkTo(parent.start)
                    top.linkTo(divider.bottom)
                }
                .clickable {
                    imageLauncher.launch("image/*")
                })

        Text(text = "javiyaraj2001", style = TextStyle(
            fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Medium
        ), modifier = Modifier
            .padding(start = 10.dp, top = 20.dp)
            .constrainAs(username) {
                start.linkTo(postCard.end)
                top.linkTo(postCard.top)
            })

        Image(painter = painterResource(id = R.drawable.ic_verified_icon),
            contentDescription = "verified",
            modifier = Modifier
                .height(15.dp)
                .width(15.dp)
                .clip(CircleShape)
                .padding(start = 5.dp, top = 20.dp)
                .constrainAs(verifiedIcon) {
                    start.linkTo(username.end)
                    top.linkTo(username.top)
                })

        BasicTextField(maxLines = 5,
            value = description,
            onValueChange = {
                description = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, top = 10.dp, end = 20.dp)
                .constrainAs(descriptionBox) {
                    start.linkTo(postCard.end)
                    end.linkTo(parent.end)
                    top.linkTo(username.bottom)
                })

        PickImage(modifier = imageModifier, imageList = selectedImages)

    }


}

@Composable
fun PickImage(modifier: Modifier, imageList: List<Uri>) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.SpaceBetween,
        columns = GridCells.Fixed(3),
        modifier = modifier
    ) {
        items(imageList.size) {
            Image(
                painter = rememberAsyncImagePainter(model = imageList[it]),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}