package com.example.gamenumbercourse.presentation

import androidx.lifecycle.ViewModel
import com.example.gamenumbercourse.data.GameRepositoryImpl
import com.example.gamenumbercourse.domain.use_case.GetGameSettingsUseCase

class GameFragmentViewModel: ViewModel() {


    private val repository = GameRepositoryImpl
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)


}