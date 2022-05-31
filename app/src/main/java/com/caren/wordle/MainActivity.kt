package com.caren.wordle

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val maxGuessesAllowed = 3

    var guessesAttempted = 0

    var wordToGuess = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        wordToGuess = "book"

        val guessTextField = findViewById<EditText>(R.id.guessTv).text

        findViewById<Button>(R.id.submitBtn).setOnClickListener {
            if (guessesAttempted >= maxGuessesAllowed) {
                // TODO: Show Toast or disable button
            }

            guessesAttempted++

            val guess = guessTextField.toString()

            val correctness = checkGuess(guess)

            // Set guess correctness
            if (guessesAttempted == 1) {
                findViewById<TextView>(R.id.guess1).text = guess
                findViewById<TextView>(R.id.guess1Check).text = correctness
            } else if (guessesAttempted == 2) {
                findViewById<TextView>(R.id.guess2).text = guess
                findViewById<TextView>(R.id.guess2Check).text = correctness
            } else if (guessesAttempted == 3) {
                findViewById<TextView>(R.id.guess3).text = guess
                findViewById<TextView>(R.id.guess3Check).text = correctness

                findViewById<TextView>(R.id.wordReveal).text = wordToGuess
            }

            // Reset text field
            guessTextField.clear()

            // Hide keyboard
            try {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }
        }
    }

    /**
    'O' can represent the right letter in the right place
    '+' can represent the right letter in the wrong place
    'X' can represent a letter not in the target word
    For example, if the target word is "DOGS", and the guess was 'GUTS', the string generated would be "+XXO".
     */
    fun checkGuess(guess: String): String {
        var stringToReturn = ""

        for (i in guess.indices) {
            // Is letter in target word
            if (wordToGuess.contains(guess[i])) {
                // Is letter in right place
                if (wordToGuess[i].equals(guess[i])) {
                    stringToReturn += "O"
                } else {
                   stringToReturn += "+"
                }
            } else {
                stringToReturn += "X"
            }
        }

        return stringToReturn
    }
}