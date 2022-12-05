
package com.example.ehhhh.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ehhhh.R
import com.example.ehhhh.models.User
import com.example.ehhhh.UserItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)


        supportActionBar?.title="Select User"

//val adapter=GroupAdapter<ViewHolder>()
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//
//        recycle_new.adapter=adapter

        fetchUser()


    }

    companion object{

      val  USER_KEY ="USER_KEY"

    }

    //*******************
    private fun fetchUser() {


        val ref=FirebaseDatabase.getInstance().getReference("/users")
ref.addListenerForSingleValueEvent(object :ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {

        var adapter=GroupAdapter<ViewHolder>()


        snapshot.children.forEach{
    val user=it.getValue(User::class.java)

    if( user!=null)
    {
        adapter.add(UserItem(user))

    }
}

        adapter.setOnItemClickListener { item, view ->

            val userItem=item as UserItem

            val i = Intent(view.context,ChatLogActivity::class.java)

            //i.putExtra(USER_KEY,userItem.user)
            i.putExtra(USER_KEY,userItem.user)
            startActivity(i)
            finish()
        }


        recycle_new.adapter=adapter


    }


    override fun onCancelled(error: DatabaseError) {
    }


})


    }

    // *************************
}