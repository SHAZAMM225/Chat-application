package com.example.ehhhh

import com.example.ehhhh.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from.view.*

class ChatItemFrom(val txt:String,val curent:User):Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {


        viewHolder.itemView.msg_from.text=txt

       Picasso.get().load(curent.profileimg).into(viewHolder.itemView.From_img)

    }

    override fun getLayout(): Int {
return R.layout.chat_from
    }
}