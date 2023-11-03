package com.example.threadapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.threadapp.R
import com.example.threadapp.viewmodels.AddThreadViewModel

@Composable
fun HomeScreen() {


    val addThreadViewModel = AddThreadViewModel()
    val postData by addThreadViewModel.postData.observeAsState()

    val placeholder = painterResource(
        id = R.drawable.ic_twitter_icon
    )

    LazyColumn() {
        postData?.let {
            items(it.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(Modifier.padding(10.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(Modifier.padding(10.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = postData!![index].first.imageUrl, placeholder = placeholder
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = postData!![index].first.username,
                                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_verified_icon),
                                contentDescription = "verified",
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp)
                            )
                        }


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.weight(0.15f)
                        ) {
                            Text(
                                text = "2h",
                                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_more_icon),
                                contentDescription = "more_icon"
                            )
                        }

                    }
                    Text(
                        text =  postData!![index].second.description,
                        modifier = Modifier.then(Modifier.padding(10.dp)),
                        style = TextStyle(fontSize = 15.sp, color = Color.Black),
                        letterSpacing = 1.sp,
                        lineHeight = 22.sp
                    )
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .then(Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = postData!![index].second.image,placeholder = placeholder),
                            contentDescription = "post_image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(modifier = Modifier.then(Modifier.padding(start = 10.dp))) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_heart_icon),
                            contentDescription = "like_icon",
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_comment_icon),
                            contentDescription = "comment_icon",
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))

                        Image(
                            painter = painterResource(id = R.drawable.ic_sync_icon),
                            contentDescription = "sync_icon",
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_share_icon),
                            contentDescription = "share_icon",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }
    }


}