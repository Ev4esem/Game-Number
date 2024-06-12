package com.example.gamenumbercourse.domain.use_case

import com.example.gamenumbercourse.domain.entities.GameSettings
import com.example.gamenumbercourse.domain.entities.Level
import com.example.gamenumbercourse.domain.repositories.GameRepository

class GetGameSettingsUseCase(
    private val repository : GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}