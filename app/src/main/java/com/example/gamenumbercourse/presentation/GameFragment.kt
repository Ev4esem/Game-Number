package com.example.gamenumbercourse.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gamenumbercourse.R
import com.example.gamenumbercourse.databinding.FragmentGameBinding
import com.example.gamenumbercourse.databinding.FragmentGameFinishedBinding
import com.example.gamenumbercourse.domain.entities.GameResult
import com.example.gamenumbercourse.domain.entities.GameSettings
import com.example.gamenumbercourse.domain.entities.Level


class GameFragment : Fragment() {
    private var _binding : FragmentGameBinding? = null
    private val binding : FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")
    private lateinit var level : Level
    private lateinit var viewModel : GameViewModel

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.tvMainNumber.setOnClickListener {
            launchGameFinishedFragment(
                GameResult(
                    true,
                    0,
                    0,
                    GameSettings(0, 0, 0, 0)
                )
            )
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }


    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let { level = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level : Level) : GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}