package com.example.gbamobileapps1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
fun OnOrOffRadio(modifier: Modifier) {

}

@Composable
fun InfoInput(modifier: Modifier = Modifier) {
    val current_context = LocalContext.current
    val main_intent = Intent(current_context, MainActivity::class.java)



    var fName by remember {mutableStateOf("")}
    var lName by remember {mutableStateOf("")}
    var housingName by remember {mutableStateOf("")}
    var floorNum by remember {mutableStateOf("")}

    val final_fname = fName
    val final_lname = lName
    val final_housingName = housingName
    val final_floornum = floorNum.toIntOrNull() ?: -1

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.howto),
            fontSize = 24.sp
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            label = { Text (stringResource(R.string.fname)) },
            value = fName,
            onValueChange = { fName = it }
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            label = { Text (stringResource(R.string.lname)) },
            value = lName,
            onValueChange = { lName = it }
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            label = { Text (stringResource(R.string.housing_name)) },
            value = housingName,
            onValueChange = { housingName = it }
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            label = { Text (stringResource(R.string.floor)) },
            value = floorNum,
            onValueChange = { floorNum = it }
        )

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick =  {
                AuthUI.getInstance().signOut(current_context).addOnCompleteListener(
                    OnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                current_context.startActivity(main_intent)
                            }
                            else -> {
                                Log.d(TAG, "Error Signing Out")
                            }
                        }
                    }
                )
                }
            ) {
                Text(
                    text = stringResource(R.string.logout)
                )
            }
            Button(onClick = {
                val db = Firebase.firestore
                val gt_student = hashMapOf(
                    "first_name" to final_fname,
                    "last_name" to final_lname,
                    "housing_building" to final_housingName,
                    "floor_num" to final_floornum
                )
                db.collection("users")
                    .add(gt_student)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Successfully Added Record: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Failed to Update Firestore Database", e)
                    }
            }) {
                Text(
                    text = stringResource(R.string.submit_text)
                )
            }
        }
    }
}