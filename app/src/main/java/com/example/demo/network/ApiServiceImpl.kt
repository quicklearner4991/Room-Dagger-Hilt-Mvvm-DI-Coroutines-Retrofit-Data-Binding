package com.example.demo.network


import com.example.demo.models.AlertsResponseClass
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getExpenseList(mHashMap: HashMap<String, Any>): AlertsResponseClass = apiService.getExpenseLists(mHashMap)
}