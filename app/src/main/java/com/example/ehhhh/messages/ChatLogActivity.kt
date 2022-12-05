package com.example.ehhhh.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ehhhh.ChatItemFrom
import com.example.ehhhh.ChatItemTo
import com.example.ehhhh.R
import com.example.ehhhh.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    val adapter=GroupAdapter<ViewHolder>()



    var toUser:User?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)


        recycle_log.adapter=adapter

        supportActionBar!!.title="Chat Log "

        toUser=  intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title=toUser!!.username


listenForMSg()
        btn_send_log.setOnClickListener {
            performMsg()

        }

    }




    private fun listenForMSg() {

//val ref=FirebaseDatabase.getInstance().getReference("/messages")

val fromId=FirebaseAuth.getInstance().uid
        val toId=toUser!!.uid


        val ref=FirebaseDatabase.getInstance().getReference("/users-messages/$fromId/$toId")


        ref.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val chatMessage=snapshot.getValue(ChatMessage::class.java)

   if ( chatMessage!=null)
   {


       if ( chatMessage.fromId == FirebaseAuth.getInstance().uid) {
           val c=LastestActivity.currentUser

           adapter.add(ChatItemFrom(chatMessage.text!!,c!!))
       }else
       {
           adapter.add(ChatItemTo(chatMessage.text,toUser))

       }



    recycle_log.scrollToPosition(adapter.itemCount-1)
   }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })

    }

    private fun performMsg() {

        //**
        val msg=txt_send.text.toString()

         //  val ref=FirebaseDatabase.getInstance().getReference("/messages").push()

        val user=  intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val fromId=FirebaseAuth.getInstance().uid

        val toId=user!!.uid


        val ref=FirebaseDatabase.getInstance().getReference("/users-messages/$fromId/$toId").push()


        val chatMessage=ChatMessage(ref.key!!,msg,fromId!!,toId,System.currentTimeMillis()/1000)
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                txt_send.text.clear()
                recycle_log.scrollToPosition(adapter.itemCount-1)
               // Toast.makeText(applicationContext,"yes saved msg",Toast.LENGTH_SHORT).show()
            }

        val toRef=FirebaseDatabase.getInstance().getReference("/users-messages/$toId/$fromId").push()



toRef.setValue(chatMessage)

        val lastesMsgRef=FirebaseDatabase.getInstance().getReference("/latest-msg/$fromId/$toId")
  lastesMsgRef.setValue(chatMessage)

        val TolastesMsgRef=FirebaseDatabase.getInstance().getReference("/latest-msg/$toId/$fromId")
        TolastesMsgRef.setValue(chatMessage)


    }
}