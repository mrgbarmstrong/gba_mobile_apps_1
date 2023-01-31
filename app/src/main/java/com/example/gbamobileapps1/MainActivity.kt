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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gbamobileapps1.ui.theme.GBAMobileApps1Theme
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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
                    HomePage()
                }
            }
        }

        var auth = FirebaseAuth.getInstance()
        var current_user = auth.getCurrentUser()

        if (current_user == null) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ga_tech_logo) // Set logo drawable
                .setTheme(R.style.Theme_GBAMobileApps1)
                .build()
            signInLauncher.launch(signInIntent)
        } else {
             val surveyIntent = Intent(this, SurveyActivity::class.java)
             startActivity(surveyIntent)
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            var user = FirebaseAuth.getInstance().currentUser
            startActivity(Intent(this, SurveyActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, SurveyActivity::class.java))
            finish()
        }
    }
}


@Preview
@Composable
fun HomePage() {
    GBAMobileApps1Theme {
        val current_context = LocalContext.current

        Column(Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Image(
                painter = painterResource(id = R.drawable.ga_tech_logo),
                contentDescription = null,
                modifier = Modifier.size(200.dp))
            Text(text = stringResource(R.string.welcome),
                style = TextStyle(fontSize = 30.sp,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center))
        }
    }
}