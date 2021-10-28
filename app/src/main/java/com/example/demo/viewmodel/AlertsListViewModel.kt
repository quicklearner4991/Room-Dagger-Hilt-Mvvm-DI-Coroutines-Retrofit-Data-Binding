package com.example.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.repository.MainRepository
import com.example.demo.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsListViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    fun getExpenseListItem(mHashMap: HashMap<String, Any>) = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getExpenseApiData(mHashMap)
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                postStateFlow.value = ApiState.Success(data)
            }
    }
}