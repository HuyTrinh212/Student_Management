package com.example.student_management

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils


import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.student_management.adapter.ListAdapter
import com.example.student_management.database.Student_Database
import com.example.student_management.model.Student_Entity

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_first_fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope, View.OnClickListener {

    private var noteDB: Student_Database? = null
    private var adapter: com.example.student_management.adapter.ListAdapter? = null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJob = Job()

        noteDB = Student_Database.getDatabase(this)

        adapter = ListAdapter(this, noteDB!!)

        rcv_list.adapter = adapter
        rcv_list.layoutManager = LinearLayoutManager(this)

        floating_action_button.setOnClickListener(this)
        reload.setOnClickListener(this)
        search.setOnClickListener(this)
//        imv_edit.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        getAllNotes()
    }

    fun getAllNotes() {
        launch {
            val notes: List<Student_Entity>? = noteDB?.studentDao()?.GetAll()
            if (notes != null) {
                adapter?.setNote(notes)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mJob.cancel()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            floating_action_button -> {
                val intent = Intent(this, first_fragment::class.java)
                startActivity(intent)
            }

            reload -> {
                getAllNotes()
            }

            search -> {
                findNote()
            }

            imv_edit -> {
                val intent = Intent(this, first_fragment::class.java)
                startActivity(intent)
                btn_add.setOnClickListener {
                    launch {
                        val strName: String = edt_name.text.toString()
                        val strEmail: String = edt_email.text.toString()
                        val strContact: String = edt_contact.text.toString()
                        val strAdress: String = edt_adress.text.toString()
                        noteDB?.studentDao()?.update(Student_Entity(name = strName, email = strEmail, sdt = strContact, adress = strAdress ))
                        finish()
                    }
                }
            }

        }


    }

    fun andap_edit(){
        val intent = Intent(this, first_fragment::class.java)
        startActivity(intent)
    }

    fun findNote() = launch {
        val strFind = et_search.text.toString()
        if (!TextUtils.isEmpty(strFind)) {
            // Find if the text is not empty
            val note: Student_Entity? = noteDB?.studentDao()?.findNoteByTitle(strFind)
            if (note != null) {
                val notes: List<Student_Entity> = mutableListOf(note)
                adapter?.setNote(notes)
            }
        } else {
            // Else get all notes
            getAllNotes()
        }
    }
}

