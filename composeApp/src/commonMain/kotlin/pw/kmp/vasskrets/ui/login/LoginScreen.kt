package pw.kmp.vasskrets.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pw.kmp.vasskrets.components.login.LoginComponent

@Composable
fun LoginScreen(component: LoginComponent) {
    val uiState by component.uiState.collectAsState()

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Your nickname:",
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = uiState.usernameInput,
            onValueChange = { component.onUsernameChanged(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(8.dp))
        TextButton(
            onClick = { component.onLoginClick() }
        ) {
            Text(text = "Connect")
        }
    }
}
