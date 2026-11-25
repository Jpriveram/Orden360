package edu.ucb.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucb.project.login.presentation.viewmodel.LoginEvent
import edu.ucb.project.login.presentation.viewmodel.LoginState

@Composable
fun SigninPage(
    state: LoginState,
    onRecovery: () -> Unit = {},
    onRegister: () -> Unit = {},
    onEvent: (LoginEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val submit = {
        focusManager.clearFocus()
        onEvent(LoginEvent.OnLoginClick)
    }

    BoxWithConstraints(Modifier.fillMaxSize().background(OrdenBackground)) {
        val viewportHeight = maxHeight
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                .heightIn(min = viewportHeight).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.size(40.dp)
                            .background(OrdenNavy, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(painterResource(R.drawable.orden_house_login), null)
                    }
                    OrdenWordmark(OrdenNavy, 20.sp, FontWeight.ExtraBold)
                }

                Text("¡Bienvenido!", color = OrdenNavy, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(6.dp))
                Text("Inicia sesión para explorar los mejores terrenos.", color = OrdenSlate, fontSize = 15.sp)
                Spacer(Modifier.height(24.dp))

                LoginField(
                    label = "Correo Electrónico",
                    placeholder = "ejemplo@correo.com",
                    icon = R.drawable.orden_mail,
                    value = state.username,
                    onValueChange = { onEvent(LoginEvent.OnUsernameChanged(it)) },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(Modifier.height(16.dp))
                LoginField(
                    label = "Contraseña",
                    placeholder = "••••••••••••",
                    icon = R.drawable.orden_lock,
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(onDone = { submit() }),
                )

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    modifier = Modifier.align(Alignment.End)
                        .clickable(role = Role.Button, onClick = onRecovery)
                        .padding(vertical = 16.dp),
                    color = OrdenGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = submit,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrdenGold, contentColor = OrdenNavy),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                ) {
                    Text("Iniciar sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                state.errorMessage?.let { message ->
                    Spacer(Modifier.height(12.dp))
                    Text(message, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                }
            }

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp, bottom = 24.dp)
                    .clickable(role = Role.Button, onClick = onRegister)
                    .padding(vertical = 12.dp),
                text = buildAnnotatedString {
                    append("¿No tienes una cuenta? ")
                    pushStyle(SpanStyle(color = OrdenGold, fontWeight = FontWeight.Bold))
                    append("Crear cuenta")
                    pop()
                },
                color = OrdenSlate,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
private fun LoginField(
    label: String,
    placeholder: String,
    icon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val shape = RoundedCornerShape(10.dp)
    val password = keyboardType == KeyboardType.Password

    Text(label, color = OrdenBlue, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(6.dp))
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().semantics { contentDescription = label },
        textStyle = TextStyle(color = OrdenNavy, fontSize = 14.sp),
        cursorBrush = SolidColor(OrdenNavy),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions,
        visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { field ->
            Row(
                modifier = Modifier.fillMaxWidth().heightIn(min = 46.dp)
                    .background(Color.White, shape)
                    .border(1.5.dp, Color(0xFFCBD5E1), shape)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painterResource(icon), null)
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(placeholder, color = OrdenSlate, fontSize = 14.sp)
                    field()
                }
            }
        },
    )
}

@Preview(showBackground = true, widthDp = 402, heightDp = 810)
@Composable
fun SigninPagePreview() {
    MaterialTheme {
        SigninPage(state = LoginState(), onEvent = {})
    }
}
