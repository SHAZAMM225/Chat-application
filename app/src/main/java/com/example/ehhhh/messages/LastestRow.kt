package com.example.ehhhh.messages

import com.example.ehhhh.R
import com.example.ehhhh.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_user.view.*
import kotlinx.android.synthetic.main.latest_mg_row.view.*

class LastestRow(val chatmsg:ChatMessage):Item<ViewHolder>() {
    var chatPartnerUser:User?=null

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.msg_last.text=chatmsg.text


        val chatPartner:String
        if(chatmsg.fromId ==FirebaseAuth.getInstance().uid)
        {
            chatPartner=chatmsg.toId
        }else
        {
            chatPartner=chatmsg.fromId
        }
        val ref=FirebaseDatabase.getInstance().getReference("/users/$chatPartner")


        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
 chatPartnerUser=snapshot.getValue(User::class.java)
                viewHolder.itemView.username_last.text=chatPartnerUser!!.username


                Picasso.get().load(chatPartnerUser!!.profileimg).into(viewHolder.itemView.img_last)

            }

            override fun onCancelled(error: DatabaseError) {


            }
        })


    }

    override fun getLayout(): Int {

return R.layout.latest_mg_row
    }
}