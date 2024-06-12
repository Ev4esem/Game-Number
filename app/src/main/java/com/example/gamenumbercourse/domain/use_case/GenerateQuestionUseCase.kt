package com.example.gamenumbercourse.domain.use_case

import com.example.gamenumbercourse.domain.entities.Question
import com.example.gamenumbercourse.domain.repositories.GameRepository

class GenerateQuestionUseCase(
    private val repository : GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {

        private const val COUNT_OF_OPTIONS = 6

    }

}