package com.example.ehhhh

import com.example.ehhhh.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_to.view.*

class ChatItemTo(val text: String,val ToUser: User?) :Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {



        viewHolder.itemView.msg_from.text=text
        Picasso.get().load(ToUser!!.profileimg).into(viewHolder.itemView.From_img)

    }

    override fun getLayout(): Int {
return R.layout.chat_to
    }
}