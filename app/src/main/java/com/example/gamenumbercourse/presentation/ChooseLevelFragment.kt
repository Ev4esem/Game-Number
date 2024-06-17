package com.example.gamenumbercourse.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.gamenumbercourse.R
import com.example.gamenumbercourse.databinding.FragmentChooseLevelBinding
import com.example.gamenumbercourse.domain.entities.Level

class ChooseLevelFragment : Fragment() {
    private var _binding : FragmentChooseLevelBinding? = null
    private val binding : FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")


    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchLevel()
    }


    private fun launchLevel() {
        with(binding) {
            cardEasy.setOnClickListener {
                launchFragment(Level.EASY)
            }
            cardNormal.setOnClickListener {
                launchFragment(Level.NORMAL)
            }
            cardHard.setOnClickListener {
                launchFragment(Level.HARD)
            }
            cardTest.setOnClickListener {
                launchFragment(Level.TEST)
            }
        }
    }

    private fun launchFragment(level : Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}