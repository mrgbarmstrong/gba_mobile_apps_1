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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gbamobileapps1.ui.theme.GBAMobileApps1Theme
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GBAMobileApps1Theme(darkTheme = false){
                Surface(modifier = Modifier
                        .background(color = MaterialTheme.colors.background)) {
                    GTInfoApp()
                }
            }
        }

        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ga_tech_logo) // Set logo drawable
                .build()
            signInLauncher.launch(signInIntent)
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
        } else {
            if (response == null) {
                //User Cancels
            } else {
                //Get Response Code
            }
        }
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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(R.string.submit_text))
        }
        /*Text Fields Here*/
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