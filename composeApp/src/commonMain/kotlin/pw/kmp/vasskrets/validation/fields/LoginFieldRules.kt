package pw.kmp.vasskrets.validation.fields

import pw.kmp.vasskrets.components.login.LoginFormField
import pw.kmp.vasskrets.validation.ValidationMessage

fun LoginFormField.rule(
    condition: Boolean,
    error: String,
    success: String,
): ValidationMessage =
    if (condition) ValidationMessage("$label: $error", ValidationMessage.Type.ERROR)
    else ValidationMessage("$label: $success", ValidationMessage.Type.SUCCESS)
