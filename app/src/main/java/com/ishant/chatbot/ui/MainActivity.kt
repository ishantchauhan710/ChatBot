package com.ishant.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishant.chatbot.R
import com.ishant.chatbot.data.Message
import com.ishant.chatbot.utils.BotResponse
import com.ishant.chatbot.utils.Constants.OPEN_GOOGLE
import com.ishant.chatbot.utils.Constants.OPEN_SEARCH
import com.ishant.chatbot.utils.Constants.RECEIVE_ID
import com.ishant.chatbot.utils.Constants.SEND_ID
import com.ishant.chatbot.utils.Time
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessagingAdapter
    private lateinit var rv: RecyclerView
    private lateinit var et: EditText
    private lateinit var btnSend: Button
    private val botList = listOf("Peter","John","Sabrina")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv_messages)
        et = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_send)

        recyclerView()
        clickEvents()
        val random = (0..3).random()
        customMessage("Hello! Today you're speaking with ${botList[random]}, how may I help you?")

    }

    private fun recyclerView() {
        adapter = MessagingAdapter(applicationContext)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage() {
        val message = et.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()) {
            et.setText("")
            adapter.insertMessage(Message(message,SEND_ID,timeStamp))
            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECEIVE_ID,timeStamp))
                rv.scrollToPosition(adapter.itemCount-1)
                when(response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchItem: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchItem")
                    }
                }
            }
        }
    }

    private fun clickEvents() {
        btnSend.setOnClickListener {
            sendMessage()
        }
        et.setOnClickListener {

            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    this@MainActivity.rv.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                this@MainActivity.rv.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timeStamp))
                rv.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}