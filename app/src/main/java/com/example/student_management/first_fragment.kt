package com.example.student_management


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.student_management.database.Student
import com.example.student_management.database.Student_Database
import com.example.student_management.model.Student_Entity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_first_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class first_fragment : AppCompatActivity(), CoroutineScope {
    private var noteDB: Student_Database ?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_fragment)
        mJob = Job()
        noteDB = Student_Database.getDatabase(this)

        btn_add.setOnClickListener {
            launch {
                val strName: String = edt_name.text.toString()
                val strEmail: String = edt_email.text.toString()
                val strContact: String = edt_contact.text.toString()
                val strAdress: String = edt_adress.text.toString()
                noteDB?.studentDao()?.insert(Student_Entity(name = strName, email = strEmail, sdt = strContact, adress = strAdress ))

                finish()

        }
}
}
}