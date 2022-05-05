package com.lgtm.emoji_diary.view.edit.validate

class ValidateEmoji {

    fun invoke(emojiId: Long): ValidateResult {
        if (emojiId < 0) {
            return ValidateResult(
                successful = false,
                errorMessage = "오늘, 기분이 어땠는지 기록해주세요."
            )
        }

        return ValidateResult(successful = true)
    }
}
