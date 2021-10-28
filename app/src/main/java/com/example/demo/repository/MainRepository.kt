package com.example.demo.repository

import com.example.demo.models.AlertsResponseClass
import com.example.demo.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getExpenseApiData(mHashMap: HashMap<String, Any>): Flow<AlertsResponseClass> = flow {
        emit(apiServiceImpl.getExpenseList(mHashMap))
    }.flowOn(Dispatchers.IO)
}