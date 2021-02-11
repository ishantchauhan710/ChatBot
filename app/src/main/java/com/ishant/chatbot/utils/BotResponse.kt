package com.ishant.chatbot.utils

import com.ishant.chatbot.utils.Constants.OPEN_GOOGLE
import com.ishant.chatbot.utils.Constants.OPEN_SEARCH

object BotResponse {
    fun basicResponses(_message: String): String {
        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when {
            message.contains("hello") -> {
                when(random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Hola!"
                    else -> "error"
                }
            }

            message.contains("how are you") -> {
                when(random) {
                    0 -> "I'm Good!"
                    1 -> "Awesome!"
                    2 -> "Pretty good!"
                    else -> "error"
                }
            }

            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if(r==0) "heads" else "tails"
                "I flipped a coin and it landed on $result"
            }

            message.contains("solve") -> {
                val equation: String? = message.substringAfter("solve")
                return try {
                    val answer = SolveMath.solveMath(equation?:"0")
                    answer.toString()
                } catch (e: Exception) {
                    "Sorry, I can't solve that!"
                }
            }

            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }

            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            message.contains("search") -> {
                OPEN_SEARCH
            }



            else -> {

                    when(random) {
                        0 -> "I don't understand"
                        1 -> "Idk"
                        2 -> "Try asking me something different!"
                        else -> "error"
                    }



            }
        }


    }
}