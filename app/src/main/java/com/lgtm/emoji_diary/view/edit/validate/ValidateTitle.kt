package com.lgtm.emoji_diary.view.edit.validate

class ValidateTitle {

    fun invoke(title: String): ValidateResult {
        if (title.isNotBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "제목을 입력해주세요."
            )
        }

        if (title.length > MAX_LENGTH) {
            return ValidateResult(
                successful = false,
                errorMessage = "제목은 30자 이내로 작성해야 합니다. (현재 : ${title.length}자)"
            )
        }

        return ValidateResult(successful = true)
    }

    companion object {
        const val MAX_LENGTH = 30
    }
}