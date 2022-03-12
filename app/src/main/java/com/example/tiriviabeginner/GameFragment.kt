package com.example.tiriviabeginner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.example.tiriviabeginner.databinding.FragmentGameBinding
import com.example.tiriviabeginner.databinding.FragmentTitleBinding


class GameFragment : Fragment() {
    data class Question(
        val text: String,
        val answer:List<String>)

    private val questions: MutableList<Question> = mutableListOf(

        Question( text = "Which is the gayest?", answer = listOf("Shmoppa", "Teh Jaila'", "Mu'zala", "Roblox's default face")),
        Question( text = "superior animal?", answer = listOf("Monke", "Floppa", "Cheems", "Goose")),
        Question( text = "Best hairstyle is?", answer = listOf("2r3a", "Bony Tail", "Mohawk", "sarsg")),
        Question( text = "test test test?", answer = listOf("one", "Two", "Three", "Four")),
        Question( text = "tsk tsk tsk", answer = listOf("1", "2", "3", "4")),
        Question( text = "Alphabets", answer = listOf("A", "B", "C", "D")),
    )

        lateinit var currentQuestion : Question
        lateinit var answers: MutableList<String>
        private var questionIndex = 0
        private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false)

        randomizeQuestions()

        binding.game = this

        binding.submitButton.setOnClickListener { view: View ->
            val checkedId = binding.rg.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId){
                var answerIndex = 0
                when (checkedId){
                    R.id.ansTwo -> answerIndex = 1
                    R.id.ansThree -> answerIndex = 2
                    R.id.ansFour -> answerIndex = 3
                }
                if (answers[answerIndex] == currentQuestion.answer[0]){
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else{
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment())
                    }
                } else{
                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }
        }
        return binding.root
    }

    private fun setQuestion(){
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answer.toMutableList()
        answers.shuffle()
    }

    private fun randomizeQuestions(){
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

}