package com.example.roomtest.view

import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.roomtest.model.Contact
import com.example.roomtest.R
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtest.model.MemoryDatabase

class ContactAdapter(var contact: ArrayList<Contact>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    class ContactViewHolder(view:View):RecyclerView.ViewHolder(view){
        var username=view.findViewById<TextView>(R.id.contactName)
        var uriText=view.findViewById<TextView>(R.id.contactPhone)
    var deleteBtn=view.findViewById<ImageView>(R.id.deleteContactBtn)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactAdapter.ContactViewHolder {
        var y=LayoutInflater.from(parent.context).inflate(R.layout.contact_rv_item,parent,false)
        return ContactViewHolder(y)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactViewHolder, position: Int) {
        holder.username.text=contact[position].contactname
        holder.uriText.text=contact[position].contactPhone
        holder.deleteBtn.setOnClickListener {

            var db= MemoryDatabase.getDatabase(holder.itemView.context)
            var contactDao= db?.getContactDao()

            var deleteItem=contactDao?.deleteContact(contact[position])
            contact.removeAt(position)
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
      return contact.size
    }
}