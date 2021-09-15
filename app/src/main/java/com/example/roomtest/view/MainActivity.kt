package com.example.roomtest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtest.R
import com.example.roomtest.model.MemeryWithPhotos
import com.example.roomtest.model.Memory
import com.example.roomtest.model.MemoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MemoryAdapter

    private var memorys:ArrayList<MemeryWithPhotos> = ArrayList<MemeryWithPhotos>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            var db = MemoryDatabase.getDatabase(applicationContext)
            var memoryDao = db?.getMemoryDao()
            // var photoDao=db?.getPhotoDao()
        lifecycleScope.launch(Dispatchers.Default) {
            var memorylista = memoryDao?.getMemoriesWithPhotos()
 withContext(Dispatchers.Main){

            if (memorylista != null) {

                memorylista?.forEach {
                    memorys.add(it)


                }


            }

            var rv = findViewById<RecyclerView>(R.id.memoryrv)
            adapter = MemoryAdapter(memorys, object : MemoryAdapter.MemoryItemListener {
                override fun addPhotoClicked(id: Int) {
                    var intent = Intent(applicationContext, AddPhotoActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }

            })
            rv.adapter = adapter
            var linearLayoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            rv.layoutManager = linearLayoutManager
            adapter.notifyDataSetChanged()

            var gotoAddMemoryBtn = findViewById<Button>(R.id.addMemoryMainBtn)
            gotoAddMemoryBtn.setOnClickListener {
                var i = Intent(applicationContext, insertMemoryActivity::class.java)
                startActivity(i)
            }
        }
        }



//       var db= MemoryDatabase.getDatabase(applicationContext)
//       var memoryDao= db?.getMemoryDao()
//        var photoDao=db?.getPhotoDao()


    //    memoryDao?.deleteMemoty(Memory().apply { id=1 })

//        var memorylist=memoryDao?.getMemoriesWithPhotos()
//       if(memorylist!=null){
//        for(m in memorylist){
//            var memory=m.memory
//            var photo=m.photos
//            Log.d("ttt",memory!!.placeName)
//            photo?.forEach{
//                Log.d("ttt",it.uri.toString())
//            }
//        }}


//                var memory1= Memory()
//        var mm= arrayListOf<Memory>(memory1)
//        var calnder=Calendar.getInstance()
//        memory1.apply {
//        placeName="Albasha Palace"
//        placeLocation="Gaza"
//        note="None"
//        visitDate=calnder.timeInMillis.toString()}
//       var inserted= memoryDao?.insertMemory(memory1)
//        var photo1= Photo()
//        photo1.uri="testing photo 1"
//        photo1.memoryId=inserted?.toInt()
//        photoDao?.insertPhoto(photo1)
//        var photo2= Photo()
//        photo2.uri="testing photo 2"
//        photo2.memoryId=inserted?.toInt()
//        photoDao?.insertPhoto(photo2)



//        var memories=memoryDao?.getAllMemorys()
//        memories?.forEach {
//            Log.d("ttt",it.id.toString())
//            Toast.makeText(applicationContext,it.id.toString(),Toast.LENGTH_LONG).show()
//        }


//        var memory=Memory()
//        memory.id=1
//        var delete=memoryDao?.deleteMemoty(memory)
//        if(delete !=-1){
//            Log.d("ttt","Deleted")
//        }

//        var memory=Memory()
//        memory.id=1
//        memory.placeName="rw"
//        memoryDao?.updateMemory(memory)


//        var memory1=Memory()
//        var calnder=Calendar.getInstance()
//        memory1.apply {
//        placeName="Albasha Palace"
//        placeLocation="Gaza"
//        note="None"
//        visitDate=calnder.timeInMillis.toString()}
//       var inserted= memoryDao?.insertMemory(memory1)
//        if(inserted !=-1L){
//            Log.d("ttt","Added ")

//        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==777&&resultCode== RESULT_OK &&data!=null){
        Log.d("ttt",data.toString())}
    }
}