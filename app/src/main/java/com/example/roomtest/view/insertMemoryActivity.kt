package com.example.roomtest.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomtest.R
import com.example.roomtest.model.Memory
import com.example.roomtest.model.MemoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class insertMemoryActivity : AppCompatActivity() {
    private var y:Int=0
    private var m:Int=0
    private var d:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_memory)

        var memoryDao=MemoryDatabase.getDatabase(applicationContext)?.getMemoryDao()

            var currentcalender=Calendar.getInstance()
        y=currentcalender.get(Calendar.YEAR)
        m=currentcalender.get(Calendar.MONTH)+1
        d=currentcalender.get(Calendar.DAY_OF_MONTH)
        findViewById<TextView>(R.id.dateTextView).text="$y/$m/$d"

        var dateBtn=findViewById<Button>(R.id.addDateBtn)
        dateBtn.setOnClickListener {
            var calender= Calendar.getInstance()
            var d= DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                this.y=year
                this.m=month+1
                this.d=dayOfMonth
                findViewById<TextView>(R.id.dateTextView).text="$y/$m/$d"

            },calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH))
            d.show()
        }

        var saveBtn=findViewById<Button>(R.id.saveMemoryBtn)
        saveBtn.setOnClickListener {
            var placeName=findViewById<EditText>(R.id.placeNameED).text.toString()
            var placeLocation=findViewById<EditText>(R.id.placeLocationED).text.toString()
            var note=findViewById<EditText>(R.id.noteED).text.toString()
            if(placeName.isNullOrEmpty() ||placeLocation.isNullOrEmpty() )
            {
                Toast.makeText(applicationContext,"Please Enter the memory and memory location",Toast.LENGTH_LONG).show()
            }else{
            var calender=Calendar.getInstance().apply {
                set(y,m,d)
            }
            var memory= Memory()
            memory.placeName=placeName
            memory.note=note
            memory.placeLocation=placeLocation
            memory.visitDate=calender.timeInMillis.toString()
                lifecycleScope.launch(Dispatchers.IO){
            var inserted=memoryDao?.insertMemory(memory)
                    withContext(Dispatchers.Main){
            if(inserted!=-1L){

                Toast.makeText(applicationContext,"Memory Added Succefuly", Toast.LENGTH_LONG).show()
            }
            var i= Intent(applicationContext,MainActivity::class.java)
            startActivity(i)
                }
                }
        }}
    }
}