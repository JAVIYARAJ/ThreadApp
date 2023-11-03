package com.example.threadapp.widgets

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CustomTextFiled(textFiled:TextFieldValue,callBack:(TextFieldValue) -> Unit,label:String,modifier: Modifier,isDoneOption:Boolean=false,focusRequester: FocusRequester?=null){
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = textFiled, onValueChange =callBack, label = {Text(text = label)}, modifier = modifier, textStyle = TextStyle(color = Color.White), keyboardOptions = KeyboardOptions.Default.copy(imeAction = if(isDoneOption)  ImeAction.Done else ImeAction.Next), keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        },

        onNext = {
            if(isDoneOption){
                focusRequester?.requestFocus()
            }
        }
    ))

}

@Composable
fun CustomButton(onTap:()->Unit,title:String,modifier: Modifier){

    Button(onClick = onTap,modifier=modifier, contentPadding = PaddingValues(15.dp), shape = RoundedCornerShape(10.dp)) {
        Text(text = title, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun AuthBottomLabel(title:String,subTitle:String,modifier: Modifier){
    Row {
       Text(text = title, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Color.White))
       BasicText(text = subTitle,style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White), modifier = modifier)
    }
}

@Composable
fun ProfileButton(title:String,onTap: () -> Unit,modifier: Modifier){
    OutlinedButton(shape = RoundedCornerShape(5.dp),onClick = onTap, modifier = modifier.height(35.dp)) {
        Text(text = title, style = TextStyle(textAlign = TextAlign.Center, color = Color.Black), modifier = Modifier.fillMaxSize())
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
