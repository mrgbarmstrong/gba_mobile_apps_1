package com.example.gbamobileapps1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.gbamobileapps1.ui.theme.GBAMobileApps1Theme
import com.firebase.ui.auth.AuthUI

class SurveyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GBAMobileApps1Theme {
                GTInfoApp()
            }
        }
    }
}

@Preview
@Composable
fun GTInfoApp() {
    GBAMobileApps1Theme {
        TopAppBar()
        InfoInput(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
        )
    }
}


@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row (modifier = modifier
        .background(color = MaterialTheme.colors.primary)
        .fillMaxWidth()
        .height(50.dp)
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.ga_tech_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier.size(50.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary)
        )
    }
}

@Composable
fun InfoInput(modifier: Modifier = Modifier) {
    val current_context = LocalContext.current
    val main_intent = Intent(current_context, MainActivity::class.java)

    Column(
        modifier = modifier
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick =  {
                AuthUI.getInstance().signOut(current_context)
                current_context.startActivity(main_intent)}
            ) {
                Text(
                    text = stringResource(R.string.logout)
                )
            }
            Button(onClick = {}) {
                Text(
                    text = stringResource(R.string.submit_text)
                )
            }
        }
    }
}