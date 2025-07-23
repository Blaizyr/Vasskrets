package pw.kmp.vasskrets.components.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val usernameInput: String = "",
    val passwordInput: String = "",
//    val usernameValidation: ValidationResult? = null,
//    val passwordValidated: ValidationResult? = null,
    val loginError: String? = null,
    val success: Boolean = false,
)
interface LoginComponent {
    val uiState: StateFlow<LoginState>
    fun onLoginClick()
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
}
class DefaultLoginComponent(val onLoginSuccess: (sessionId: String) -> Unit, componentContext: ComponentContext) :  LoginComponent,  ComponentContext by componentContext, InstanceKeeper.Instance {
    override val uiState: StateFlow<LoginState>
        get() = TODO("Not yet implemented")

    override fun onLoginClick() {
        TODO("Not yet implemented")
    }

    override fun onUsernameChanged(username: String) {
        TODO("Not yet implemented")
    }

    override fun onPasswordChanged(password: String) {
        TODO("Not yet implemented")
    }
}
