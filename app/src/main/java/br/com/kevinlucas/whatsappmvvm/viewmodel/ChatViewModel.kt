package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.model.ChatModel
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.service.repository.ChatRepository

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val mChatRepository = ChatRepository(application)

    private val mSendMessage = MutableLiveData<ValidationListener>()
    private val mMessagesList = MutableLiveData<List<MessageModel>>()
    private val mTalksList = MutableLiveData<List<ChatModel>>()
    private val mValidation = MutableLiveData<ValidationListener>()

    fun send(): LiveData<ValidationListener> {
        return mSendMessage
    }

    fun messages(): LiveData<List<MessageModel>> {
        return mMessagesList
    }

    fun talks(): LiveData<List<ChatModel>> {
        return mTalksList
    }

    fun sendMessageChat(contactId: String, message: String) {

        val listener = object : APIListener<Boolean> {
            override fun onSuccess(validation: Boolean) {
                mSendMessage.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mSendMessage.value = ValidationListener(str)
            }
        }

        mChatRepository.sendMessage(contactId, message, listener)
    }

    fun loadMessages(contactId: String, event: String) {
        val listener = object : APIListener<List<MessageModel>> {
            override fun onSuccess(model: List<MessageModel>) {
                mMessagesList.value = model
            }

            override fun onFailure(str: String) {
                mMessagesList.value = arrayListOf()
                mValidation.value = ValidationListener(str)
            }
        }

        mChatRepository.loadAllMessages(listener, event, contactId)
    }

    fun saveChat(contactId: String, nameContact: String, message: String) {
        mChatRepository.saveChat(contactId, nameContact, message)
    }

    fun list(event: String) {
        val listener = object : APIListener<List<ChatModel>> {
            override fun onSuccess(model: List<ChatModel>) {
                mTalksList.value = model
            }

            override fun onFailure(str: String) {
                mTalksList.value = arrayListOf()
                mValidation.value = ValidationListener(str)
            }
        }

        mChatRepository.all(listener, event)

    }


}