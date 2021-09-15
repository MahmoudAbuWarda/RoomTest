package com.example.roomtest.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomtest.R
import com.example.roomtest.model.MemoryDatabase
import com.example.roomtest.model.Photo

class PhotoAdapter(var photos: ArrayList<Photo>): RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    class PhotoViewHolder(view: View):RecyclerView.ViewHolder(view){
        var photoRvIm:ImageView=view.findViewById(R.id.photoRVImageView)
        var deleteBtn:ImageView=view.findViewById(R.id.removePhotoBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        var y=LayoutInflater.from(parent.context).inflate(R.layout.photo_rv_item,parent,false)
        return PhotoViewHolder(y)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(Uri.parse(photos[position].uri)).into(holder.photoRvIm)
     //   holder.photoRvIm.setImageURI(Uri.parse(photos[position].uri))
        holder.deleteBtn.setOnClickListener {

            var db= MemoryDatabase.getDatabase(holder.itemView.context)
            var photoDao= db?.getPhotoDao()
            var deleteItem=photoDao?.deletePhoto(photos[position])
            photos.removeAt(position)
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}
