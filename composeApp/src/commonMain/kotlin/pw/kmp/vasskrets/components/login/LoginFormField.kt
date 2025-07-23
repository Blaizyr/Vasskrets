package pw.kmp.vasskrets.components.login

import pw.kmp.vasskrets.validation.ValidationMessage
import pw.kmp.vasskrets.validation.ValidationResult
import pw.kmp.vasskrets.validation.fields.rule

sealed class LoginFormField {
    abstract val label: String
    abstract val hint: String
    abstract val isSensitive: Boolean
    abstract suspend fun validate(value: String): ValidationResult

    data object Username : LoginFormField() {
        override val label = "Username"
        override val hint = "Enter your username"
        override val isSensitive = false

        override suspend fun validate(value: String): ValidationResult {
            val username: String = value
            val messages = listOf(
                rule(
                    username.length < 3,
                    "Username must be at least 3 characters",
                    "Username length OK"
                ),
                rule(
                    !username.all { it.isLetterOrDigit() || it == '_' },
                    "Invalid characters in username",
                    "Username characters OK"
                )
            )
            return ValidationResult(
                isValid = messages.none { it.type == ValidationMessage.Type.ERROR },
                messages = messages
            )
        }

    }

    data object Password : LoginFormField() {
        override val label = "Password"
        override val hint = "Enter your password"
        override val isSensitive = true

        override suspend fun validate(value: String): ValidationResult {
            val password: String = value
            val specialChars = "!@#$%^&*()_+~`|}{[]:;?><,./-="
            val messages = listOf(
                rule(
                    password.length < 8,
                    "must be at least 8 characters",
                    "length OK"
                ),
                rule(
                    !password.any { it.isUpperCase() },
                    "At least one uppercase letter required",
                    "Has uppercase letter"
                ),
                rule(
                    !password.any { it.isLowerCase() },
                    "At least one lowercase letter required",
                    "Has lowercase letter"
                ),
                rule(
                    !password.any { it.isDigit() },
                    "At least one digit required",
                    "Has digit"
                ),
                rule(
                    !password.any { specialChars.contains(it) },
                    "At least one special character required",
                    "Has special character"
                ),
            )
            return ValidationResult(
                isValid = messages.none { it.type == ValidationMessage.Type.ERROR },
                messages = messages
            )
        }
    }

    companion object {
        val all = listOf(Username, Password)
    }
}
