package com.example.roomtest.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts.CONTENT_URI
import android.provider.ContactsContract.Profile.CONTENT_URI
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtest.R
import com.example.roomtest.model.Contact
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomtest.model.MemoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class AddContactActivity : AppCompatActivity() {
    private lateinit var uri: Uri
    private var contacts: ArrayList<Contact> = ArrayList<Contact>()
    private lateinit var adapter: ContactAdapter
    private var intentid:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        intentid=intent.getIntExtra("id",0)

        var db= MemoryDatabase.getDatabase(applicationContext)
        var memoryDao= db?.getMemoryDao()
        //  var photoDao=db?.getPhotoDao()

        var memorylista=memoryDao?.getMemoeryWithContacts(intentid)

        if(memorylista!=null){
            for(m in memorylista.contact!!){
                //  var memory=m.memory
                // var photo=m
                contacts.add(m)

//                photo?.forEach{
//
//                    photos.add(it)
//
//
//                }
            }}



        var rv=findViewById<RecyclerView>(R.id.contactRV)
        adapter=ContactAdapter(contacts)
        rv.adapter=adapter
        var linearLayoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        rv.layoutManager=linearLayoutManager

        var addContactBtn=findViewById<Button>(R.id.addContacntBtn)
        addContactBtn.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),444)
            }else{
                addContact()
            }
        }

        var saveContactBtn=findViewById<TextView>(R.id.saveContactBtn)
        saveContactBtn.setOnClickListener {
            var contactDao= MemoryDatabase.getDatabase(applicationContext)?.getContactDao()
            lifecycleScope.launch(Dispatchers.IO){
              contactDao?.insertContacts(contacts)
                withContext(Dispatchers.Main){

                var i= Intent(applicationContext,MainActivity::class.java)
                startActivity(i)
            }
            }

        }
        adapter.notifyDataSetChanged()


    }
    private fun addContact(){
//var contactFile=Contact.getUri

//       var contactUri=ContactsContract.Data.CONTENT_URI
//        Log.d("ttt",contactUri.toString())
        var i= Intent(Intent.ACTION_PICK)
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)

        startActivityForResult(i,666)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==666){
            if(resultCode== RESULT_OK&&data!=null){
                //var projection= ArrayList<Contact>()
                uri=data?.getData() as Uri

                var cursor=contentResolver.query(uri,null,null,null,null)
                if(cursor !=null){
                cursor.moveToFirst()
                var phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                var nameIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                var phoneNumber=cursor.getString(phoneIndex)
                    var phoneName=cursor.getString(nameIndex)

                var contac=Contact(contactname=phoneName,contactPhone=phoneNumber,contactUri=data.toString(),memoryId = intentid)
                    contacts.add(contac)
                    for(c in contacts){
                        Log.d("ttt",contacts.toString())
                    }

                Log.d("ttt","${data.toString()} $phoneNumber $phoneName")}
                Toast.makeText(applicationContext,"Conntact Added Successfuly",Toast.LENGTH_LONG).show()
                adapter.notifyDataSetChanged()
            }
        }
    }

}