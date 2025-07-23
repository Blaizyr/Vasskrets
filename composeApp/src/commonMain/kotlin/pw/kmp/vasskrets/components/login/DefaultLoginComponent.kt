package pw.kmp.vasskrets.components.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.validation.ValidationResult

data class LoginState(
    val usernameInput: String = "",
    val passwordInput: String = "",
    val usernameValidation: ValidationResult? = null,
    val passwordValidated: ValidationResult? = null,
    val loginError: String? = null,
    val success: Boolean = false,
)

interface LoginComponent {
    val uiState: StateFlow<LoginState>
    fun onLoginClick()
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
}

@FlowPreview
class DefaultLoginComponent(
    val onLoginSuccess: (sessionId: String) -> Unit,
//    val loginUseCase: LoginUseCase = DefaultLoginUseCase(gameClient),
    componentContext: ComponentContext,
) : LoginComponent, ComponentContext by componentContext, InstanceKeeper.Instance {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow(LoginState())
    override val uiState = _uiState.asStateFlow()

    init {
        setupValidation()
    }

    override fun onUsernameChanged(username: String) {
        _uiState.update { it.copy(usernameInput = username) }
        inputFlows[LoginFormField.Username]?.tryEmit(username)
    }

    override fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(passwordInput = password) }
        inputFlows[LoginFormField.Password]?.tryEmit(password)
    }

    override fun onLoginClick() {
        coroutineScope.launch {
            login()
        }
    }

    override fun onDestroy() {
        println("LoginComponent destroyed")
        coroutineScope.cancel()
    }

    private fun setupValidation() {
        inputFlows.forEach { (key, flow) ->
            coroutineScope.launch {
                flow
                    .debounce(350)
                    .distinctUntilChanged()
                    .collectLatest { value ->
                        validateAndUpdateState(key, value)
                    }
            }
        }
    }

    private suspend fun validateAndUpdateState(key: LoginFormField, value: String) {
        val validation = key.validate(value)
        _uiState.update { state ->
            when (key) {
                LoginFormField.Username -> state.copy(
                    usernameInput = value,
                    usernameValidation = validation
                )

                LoginFormField.Password -> state.copy(
                    passwordInput = value,
                    passwordValidated = validation
                )
            }
        }
    }

    private suspend fun login() {
        val state = uiState.value
        val usernameValid = state.usernameValidation?.isValid == true
        val passwordValid = state.passwordValidated?.isValid == true

        if (!usernameValid /*|| !passwordValid*/) return
        _uiState.update { it.copy(loginError = null) }

        val result = Result.success("sessionId") //loginUseCase(state.usernameInput, state.passwordInput)

        result.onSuccess { session ->
            onLoginSuccess(session)
            _uiState.update {
                it.copy(
                    usernameInput = "",
                    passwordInput = "",
                    success = true,
                    loginError = null
                )
            }
        }
        result.onFailure { e ->
            _uiState.update {
                it.copy(
                    loginError = e.message ?: "Unknown login error",
                    passwordInput = "",
                )
            }
        }
    }
}

private val inputFlows = LoginFormField.all.associateWith {
    MutableSharedFlow<String>(replay = 1)
}
