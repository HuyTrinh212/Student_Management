package com.example.student_management.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student_Entity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo( name = "name") val name: String,
    @ColumnInfo( name = "email") val email: String,
    @ColumnInfo( name = "sdt" ) val sdt: String,
    @ColumnInfo( name = "adress" ) val adress: String
        )