package com.example.ehhhh.registerLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ehhhh.R
import com.example.ehhhh.messages.LastestActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        send_login.setOnClickListener {
            signin()
        }

        create_acc.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    fun signin()
    {

        val mail=mail_in.text.toString()
        val pass=pass_in.text.toString()

  val ref=FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,pass)
      .addOnSuccessListener {
//Toast.makeText(applicationContext,"yes",Toast.LENGTH_SHORT).show()

          startActivity(Intent(applicationContext,LastestActivity::class.java))
      }

    }
}