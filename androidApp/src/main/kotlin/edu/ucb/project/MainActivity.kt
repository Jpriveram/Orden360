package edu.ucb.project

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import edu.ucb.project.login.presentation.viewmodel.LoginEvent
import edu.ucb.project.login.presentation.viewmodel.LoginState
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OrdenOnboarding { showingWelcome ->
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !showingWelcome
                    isAppearanceLightNavigationBars = !showingWelcome
                }
            }
        }
    }
}

@Composable
private fun OrdenOnboarding(updateSystemBars: (Boolean) -> Unit) {
    var showingWelcome by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (showingWelcome) {
            delay(1_500)
            showingWelcome = false
        }
    }
    SideEffect { updateSystemBars(showingWelcome) }

    MaterialTheme(colorScheme = lightColorScheme()) {
        Box(
            Modifier.fillMaxSize()
                .background(if (showingWelcome) OrdenNavy else OrdenBackground)
                .windowInsetsPadding(WindowInsets.safeDrawing),
        ) {
            if (showingWelcome) {
                WelcomeScreen()
            } else {
                LoginDemo()
            }
        }
    }
}

@Composable
private fun LoginDemo() {
    var state by remember { mutableStateOf(LoginState()) }

    SigninPage(
        state = state,
        onRecovery = {
            state = state.copy(errorMessage = "La recuperación de contraseña aún no está disponible.")
        },
        onRegister = {
            state = state.copy(errorMessage = "El registro aún no está disponible.")
        },
    ) { event ->
        state = when (event) {
            is LoginEvent.OnUsernameChanged -> state.copy(username = event.value, errorMessage = null)
            is LoginEvent.OnPasswordChanged -> state.copy(password = event.value, errorMessage = null)
            LoginEvent.OnLoginClick -> state.copy(errorMessage = validateDemoLogin(state))
        }
    }
}

private fun validateDemoLogin(state: LoginState): String = when {
    !Patterns.EMAIL_ADDRESS.matcher(state.username.trim()).matches() -> "Ingresa un correo válido."
    state.password.isBlank() -> "Ingresa tu contraseña."
    else -> "Formulario válido. La autenticación aún no está conectada."
}
