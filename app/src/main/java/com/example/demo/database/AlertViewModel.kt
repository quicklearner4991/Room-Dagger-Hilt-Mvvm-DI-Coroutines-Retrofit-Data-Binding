package com.example.demo.database

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo.models.Alert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel
@Inject
constructor(private val userRepository: AlertRepository) : ViewModel(){

    val getList:LiveData<List<Alert>> get() =
        userRepository.getList.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)

    fun insert(user:Alert){
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    fun delete(user:Alert){
        viewModelScope.launch {
            userRepository.delete(user)
        }
    }
}