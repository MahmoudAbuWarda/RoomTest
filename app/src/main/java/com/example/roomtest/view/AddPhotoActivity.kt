package com.example.roomtest.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.roomtest.R
import java.io.File
import java.util.*
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toFile
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtest.model.MemoryDao
import com.example.roomtest.model.MemoryDatabase
import com.example.roomtest.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class AddPhotoActivity : AppCompatActivity() {
    private lateinit var uri:Uri

    private val REQUEST_IMAGE_CAPTURE=222
    private val REQUEST_IMAGE_GALLERY=333
    private var photos:ArrayList<Photo> = ArrayList<Photo>()
    private var intentid:Int=0
    private lateinit var adapter: PhotoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        intentid=intent.getIntExtra("id",0)
            Log.d("ttt","Intent $intentid")

        var db= MemoryDatabase.getDatabase(applicationContext)
        var memoryDao= db?.getMemoryDao()
      //  var photoDao=db?.getPhotoDao()
        lifecycleScope.launch(Dispatchers.IO){
        var memorylista=memoryDao?.getMemoryWithPhoto(intentid)
        withContext(Dispatchers.Main){
        if(memorylista!=null){
            for(m in memorylista.photos!!){
              //  var memory=m.memory
               // var photo=m
                photos.add(m)

//                photo?.forEach{
//
//                    photos.add(it)
//
//
//                }
            }}





       var rv=findViewById<RecyclerView>(R.id.photoRV)
       adapter=PhotoAdapter(photos)
       rv.adapter=adapter
        var gridLayout= GridLayoutManager(applicationContext,3,RecyclerView.VERTICAL,false)
    //   var linearLayoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
       rv.layoutManager=gridLayout
        }
        }


        var camerPhotoBtn=findViewById<Button>(R.id.capturePhotoBtn)
        camerPhotoBtn.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CAMERA),111)
            }else{
                capturePhoto()
            }
        }

        var fromGalleryBtn=findViewById<Button>(R.id.fromGalleryBtn)
        fromGalleryBtn.setOnClickListener {


            //  val pickPhoto= Intent(Intent.ACTION_PICK, FileProvider.getUriForFile(this,"com.example.roomtest.view",File(getExternalFilesDir(null), "image" + Calendar.getInstance().timeInMillis + ".jpg")))
            var pickPhoto=Intent(Intent.ACTION_PICK)

            pickPhoto.type="image/*"

            pickPhoto.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto,REQUEST_IMAGE_GALLERY)
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            uri = FileProvider.getUriForFile(
//                this,
//                "com.example.roomtest.view",
//                File(
//                    getExternalFilesDir(null),
//                    "image" + Calendar.getInstance().timeInMillis + ".jpg"
//                )
//            )

//            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }

        var savePhotoBtn=findViewById<Button>(R.id.savePhotoBtn)
        savePhotoBtn.setOnClickListener {
            var photoDao = MemoryDatabase.getDatabase(applicationContext)?.getPhotoDao()
            lifecycleScope.launch(Dispatchers.IO) {
                photoDao?.insertPhotos(photos)
                withContext(Dispatchers.Main){


//                val saveBtn=Intent(photos[0].uri)
//
//               startActivityForResult(saveBtn,777)


                var i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                }
            }
        }
    }
    private fun capturePhoto(){
        //  var imageFile= File(getExternalFilesDir(null),"IMAGE_"+SimpleDateFormater("yyyyMMdd_HHmmss").format(Date())+".PNG")


        var imageFile= File(getExternalFilesDir(null),"IMAGE_"+ Calendar.getInstance().timeInMillis.toString()+".PNG")


        uri= FileProvider.getUriForFile(applicationContext,"com.example.roomtest.view",imageFile)

        var i=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        i.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        startActivityForResult(i,REQUEST_IMAGE_CAPTURE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(this::uri.isInitialized&&resultCode==RESULT_OK){

                var photo=Photo()

             //   photo.id

                photo.memoryId=intentid
                photo.uri=uri.toString()

             photos.add(photo)

                    adapter.notifyDataSetChanged()}

                //  Log.d("ttt","add ${uri.toString()} to recyclerview From Camera")

        }//else if(requestCode== RESULT_OK &&requestCode==333){
        else if(requestCode==REQUEST_IMAGE_GALLERY){
            if( data!=null && resultCode== RESULT_OK){
               uri= FileProvider.getUriForFile(this,"com.example.roomtest.view",File(getExternalFilesDir(null),"IMAGE_"+ Calendar.getInstance().timeInMillis.toString()+".PNG"))
               // uri=data?.getData() as Uri
                if (this::uri.isInitialized){
                    uri = FileProvider.getUriForFile(
                        this,
                        "com.example.roomtest.view",
                        File(
                            getExternalFilesDir(null),
                            saveImageToExternalStorage(
                                MediaStore.Images.Media.getBitmap(
                                    getContentResolver(),
                                    data?.data
                                )
                            ).toString().substringAfterLast("/")
                        )
                    )
                var photo=Photo()
                photo.memoryId=intentid
             //   uri.
                photo.uri=uri.toString()
                photos.add(photo)}
              adapter.notifyDataSetChanged()

                // Log.d("ttt","add ${uri.toString()} to recyclerview From Gallery")
            }
        }

    }
    private fun saveImageToExternalStorage(bitmap: Bitmap): Uri {
        // Get the external storage directory path
        val path = getExternalFilesDir(null)

        // Create a file to save the image
        val file = File(path, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the output stream
            stream.flush()

            // Close the output stream
            stream.close()
            Toast.makeText(applicationContext, "Image Added successful.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error in Add image.", Toast.LENGTH_SHORT).show()
        }

        // Return the saved image path to uri
        return Uri.parse(file.absolutePath)
    }
}