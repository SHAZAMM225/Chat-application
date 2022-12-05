package com.example.ehhhh

import com.example.ehhhh.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row.view.*

class UserItem(val user: User):Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.name_row.text=user.username

        Picasso.get().load(user.profileimg).into(viewHolder.itemView.img_row)
    }

    override fun getLayout(): Int {
return R.layout.user_row
    }
}