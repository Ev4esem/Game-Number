package com.example.gamenumbercourse.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamenumbercourse.R
import com.example.gamenumbercourse.data.GameRepositoryImpl
import com.example.gamenumbercourse.domain.entities.GameResult
import com.example.gamenumbercourse.domain.entities.GameSettings
import com.example.gamenumbercourse.domain.entities.Level
import com.example.gamenumbercourse.domain.entities.Question
import com.example.gamenumbercourse.domain.use_case.GenerateQuestionUseCase
import com.example.gamenumbercourse.domain.use_case.GetGameSettingsUseCase

class GameViewModel(
    private val context : Application,
    private val level : Level
) : ViewModel() {

    private val repository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private lateinit var gameSettings : GameSettings
    private var timer : CountDownTimer? = null
    private val _questionData = MutableLiveData<Question>()
    val questionData : LiveData<Question> = _questionData
    private val _formatterTime = MutableLiveData<String>()
    val formatterTime : LiveData<String> = _formatterTime
    private var countOfRightAnswer = 0
    private var countOfQuestion = 0
    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers : LiveData<Int> = _percentOfRightAnswers
    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers : LiveData<String> = _progressAnswers
    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount : LiveData<Boolean> = _enoughCount
    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent : LiveData<Boolean> = _enoughPercent
    private val _minPercent = MutableLiveData<Int>()
    val minPercent : LiveData<Int> = _minPercent
    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult : LiveData<GameResult> = _gameResult

    init {
        startGame()
    }

    private fun startGame() {
        getGameSetting()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(answer : Int) {
        checkAnswer(answer)
        updateProgress()
        generateQuestion()
    }

    @SuppressLint("StringFormatMatches")
    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers() : Int {
        if (countOfRightAnswer == 0) {
            return 0
        }
        return ((countOfRightAnswer / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number : Int) {
        val rightAnswer = _questionData.value?.rightAnswer
        if (rightAnswer == number) {
            countOfRightAnswer ++
        }
        countOfQuestion ++
    }

    private fun generateQuestion() {
        _questionData.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun getGameSetting() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        val parseSecondsInMillis = gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS
        timer = object : CountDownTimer(parseSecondsInMillis, MILLIS_IN_SECONDS) {
            override fun onTick(millisUntilFinished : Long) {
                _formatterTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(millisUntilFinished : Long) : String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswer,
            countOfQuestion,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}