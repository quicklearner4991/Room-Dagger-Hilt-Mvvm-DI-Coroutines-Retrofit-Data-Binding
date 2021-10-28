package com.example.demo.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert")
data class Alert(
    val alert_frequency: String,
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val alert_id: Int,
    val alert_location: String,
    val alert_message: String,
    val alert_module_type: String,
    val alert_priority: String,
    val expiration_date: String,
    val isdeleted: Int,
    val marine_id: Int,
    val timestamp: String
)