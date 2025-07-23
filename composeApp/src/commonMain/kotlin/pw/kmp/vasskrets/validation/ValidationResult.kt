package pw.kmp.vasskrets.validation

data class ValidationResult(
    val isValid: Boolean,
    val messages: List<ValidationMessage>,
)
