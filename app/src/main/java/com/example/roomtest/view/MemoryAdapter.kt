package com.example.roomtest.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomtest.R
import com.example.roomtest.model.MemeryWithPhotos
import com.example.roomtest.model.Memory
import com.example.roomtest.model.MemoryDatabase


class MemoryAdapter(var memorys: ArrayList<MemeryWithPhotos>,var listener: MemoryItemListener): RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder>() {

    class MemoryViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var memoryNameText=view.findViewById<TextView>(R.id.memoryNameText)
        var memoryNote=view.findViewById<TextView>(R.id.memoryNote)
        var deleteBtn=view.findViewById<ImageView>(R.id.deleteContactBtn)
        var gotoPhotos=view.findViewById<Button>(R.id.addPhotoMainBtn)
        var gotoContacts=view.findViewById<Button>(R.id.addContacsMainBtn)
        var memoryPhoto=view.findViewById<ImageView>(R.id.memoryPhoto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        var y= LayoutInflater.from(parent.context).inflate(R.layout.memory_rv_item,parent,false)
        return MemoryViewHolder(y)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        var memoryWithPhotos=memorys[position]
        if(!memoryWithPhotos.photos.isNullOrEmpty()){
            //holder.memoryPhoto.setImageURI(Uri.parse(memoryWithPhotos.photos?.shuffled()?.get(0)?.uri))
            Glide.with(holder.itemView.context).load(Uri.parse(memoryWithPhotos.photos?.shuffled()?.get(0)?.uri)).into(holder.memoryPhoto)

        }
        holder.memoryNameText.text=memorys[position].memory?.placeName
        holder.memoryNote.text=memorys[position].memory?.note
        holder.deleteBtn.setOnClickListener {

            var db= MemoryDatabase.getDatabase(holder.itemView.context)
            var memoryDao= db?.getMemoryDao()
            var deleteItem= memoryDao?.deleteMemoty(memorys[position]?.memory!!)
            memorys.removeAt(position)
            notifyDataSetChanged()
        }
        holder.gotoContacts.setOnClickListener {
            var i= Intent(holder.itemView.context,AddContactActivity::class.java)
            i.putExtra("id",memorys[position].memory?.id)
            holder.itemView.context.startActivity(i)
        }
        holder.gotoPhotos.setOnClickListener {
//            var i= Intent(holder.itemView.context,AddPhotoActivity::class.java)
//            i.putExtra("id",memorys[position].id)
//            holder.itemView.context.startActivity(i)
            listener.addPhotoClicked(memorys.get(position).memory?.id?.toInt() as Int)
        }
    }

    override fun getItemCount(): Int {
      return memorys.size
    }

    interface MemoryItemListener{
        public fun addPhotoClicked(id:Int)
    }
}