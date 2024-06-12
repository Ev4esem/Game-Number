package com.example.gamenumbercourse.domain.repositories

import com.example.gamenumbercourse.domain.entities.GameSettings
import com.example.gamenumbercourse.domain.entities.Level
import com.example.gamenumbercourse.domain.entities.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level : Level): GameSettings

}