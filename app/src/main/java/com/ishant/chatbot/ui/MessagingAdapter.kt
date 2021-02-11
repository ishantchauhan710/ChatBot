package com.ishant.chatbot.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ishant.chatbot.R
import com.ishant.chatbot.data.Message
import com.ishant.chatbot.utils.Constants.RECEIVE_ID
import com.ishant.chatbot.utils.Constants.SEND_ID

class MessagingAdapter(private val context: Context): RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnLongClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Delete Message")
                    .setMessage("Are you sure you want to delete this message")
                    .setPositiveButton("Yes") { d, i ->
                        messagesList.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        d.dismiss()
                    }.setNegativeButton("No") { d, i ->
                        d.dismiss()
                    }.create().show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        val myMsg = holder.itemView.findViewById<TextView>(R.id.tv_message)
        val botMsg = holder.itemView.findViewById<TextView>(R.id.tv_bot_message)

        when (currentMessage.id) {
            SEND_ID -> {
                myMsg.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                botMsg.visibility = View.GONE
            }
            RECEIVE_ID -> {
                botMsg.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                myMsg.visibility = View.GONE
            }


        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    fun insertMessage(message: Message) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }
}