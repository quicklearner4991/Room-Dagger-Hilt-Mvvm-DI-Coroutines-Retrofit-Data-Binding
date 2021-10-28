package com.example.demo.database


import androidx.annotation.WorkerThread
import com.example.demo.models.Alert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlertRepository @Inject constructor (private val alertDao: AlertDao) {
    val getList:Flow<List<Alert>> = alertDao.getList()

    @WorkerThread
    suspend fun insert(alert:Alert) = withContext(Dispatchers.IO){
        alertDao.insert(alert)
    }

    @WorkerThread
    suspend fun delete(alert:Alert) = withContext(Dispatchers.IO){
        alertDao.delete(alert)
    }
}