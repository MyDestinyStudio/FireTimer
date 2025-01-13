package my.destinyStudio.firetimer.screens.loginScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SignUpScreen( ) {



        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)

        ) {
            Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {

                NormalTextComponent(value = "Hello")
                HeadingTextComponent(value =   "Create Account")
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = "first name ",

                    onTextChanged = {

                    },
                    errorStatus = true
                )

                MyTextFieldComponent(
                    labelValue = "Last name",

                    onTextChanged = {

                    },
                    errorStatus = false
                )

                MyTextFieldComponent(
                    labelValue = "Email",

                    onTextChanged = {

                    },
                    errorStatus = false
                )

                PasswordTextFieldComponent(
                    labelValue = "Password",

                    onTextSelected = {

                    },
                    errorStatus = false
                )

                CheckboxComponent(value =  "terms_and_conditions" ,
                    onTextSelected = {

                    },
                    onCheckedChange = {

                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = " register ",
                    onButtonClicked = {

                    },
                    isEnabled = false
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {

                })
            }

        }


    }


