package com.example.ehhhh.messages

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ehhhh.R
import com.example.ehhhh.models.User
import com.example.ehhhh.registerLogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_lastest.*

class LastestActivity : AppCompatActivity() {
    companion object{
        var currentUser:User?=null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lastest)




//        supportActionBar!!.setBackgroundDrawable(ColorDrawable(
//            ContextCompat.getColor(getApplicationContext(), R.color.black)
//        ))

        adapter.setOnItemClickListener { item, view ->

  val i = Intent(this,ChatLogActivity::class.java)

            val row=item as LastestRow

            i.putExtra(NewMessageActivity.USER_KEY,row.chatPartnerUser)
            startActivity(i)

        }



        fetchCurentUser()


        recycle_latest.adapter=adapter

        recycle_latest.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))


        verifyUserLogIn()

       // setupDumy()
        ListenFor()

    }

    val map=HashMap<String,ChatMessage>()

    fun refrechRecyle(){
        adapter.clear()
        map.values.forEach{
            adapter.add(LastestRow(it))
        }
    }
    fun ListenFor()
    {
          val fromId=FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("/latest-msg/$fromId")
        ref.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                val chatMessage=snapshot.getValue(ChatMessage::class.java)?:return

                map[snapshot.key!!]=chatMessage
                refrechRecyle()


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {


                val chatMessage=snapshot.getValue(ChatMessage::class.java)?:return

                map[snapshot.key!!]=chatMessage
                refrechRecyle()

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {


            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {


            }

            override fun onCancelled(error: DatabaseError) {


            }


        })

    }

    val adapter = GroupAdapter<ViewHolder>()

//    private fun setupDumy() {
//
//
//        adapter.add(LastestRow())
//        adapter.add(LastestRow())
//
//    }
    private fun fetchCurentUser() {


        var ui=FirebaseAuth.getInstance().uid
   var ref=FirebaseDatabase.getInstance().getReference("/users/$ui")
ref.addListenerForSingleValueEvent(object :ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {

        currentUser=snapshot.getValue(User::class.java)


    }

    override fun onCancelled(error: DatabaseError) {

    }


})

    }

    private fun verifyUserLogIn() {
        val uid = FirebaseAuth.getInstance().uid
        if ( uid == null)
        {
            val i=Intent(applicationContext, RegisterActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(i)
        }    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {

            R.id.new_msg ->{

                startActivity(Intent(applicationContext, NewMessageActivity::class.java))

            }

                        R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val i = Intent(applicationContext, RegisterActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            }

        }

return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.nav_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }
}