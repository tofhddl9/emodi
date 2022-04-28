package com.lgtm.emoji_diary.view.edit.validate

class ValidateContent {

    fun invoke(content: String): ValidateResult {
        if (content.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "일기의 내용을 작성해주세요."
            )
        }

        return ValidateResult(successful = true)
    }
}