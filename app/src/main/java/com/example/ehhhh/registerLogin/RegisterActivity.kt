package com.example.ehhhh.registerLogin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.ehhhh.R
import com.example.ehhhh.messages.LastestActivity
import com.example.ehhhh.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    var selectedPhotoUri: Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        img_signUp.setOnClickListener{

            val i= Intent(Intent.ACTION_PICK)
            i.type="image/*"
            startActivityForResult(i,0)
        }

        send_up.setOnClickListener {
performRegister()
        }

        already_have.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))

        }


}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == 0 && resultCode==Activity.RESULT_OK
            && data!=null)
        {
            // uri represent the location where the photo is on the device
            selectedPhotoUri=data.data
            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            select_photoimg.setImageBitmap(bitmap)
//            val bitmapDrawable =BitmapDrawable(bitmap)
//            img_signUp.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImg() {

        val filename=UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
             //   Toast.makeText(applicationContext,"Upload Succes ",Toast.LENGTH_SHORT).show()
             ref.downloadUrl?.addOnSuccessListener {
                 saveUserTodb(it.toString())
             }
            }
    }

    fun  performRegister()
    {
        val mail=edit_mail.text.toString()
        val pass=edit_pass.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,pass)
            .addOnCompleteListener {
                if ( !it.isSuccessful) return@addOnCompleteListener
                //else
               // Toast.makeText(applicationContext,"Yess",Toast.LENGTH_SHORT).show()
uploadImg()
            }
    }


    fun saveUserTodb(profileimg:String)
    {
        val uid=FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user= User(uid!!,edit_user_name.text.toString(),profileimg)

        ref.setValue(user)
            .addOnSuccessListener {
             //   Toast.makeText(applicationContext,"hhh bom",Toast.LENGTH_SHORT).show()

                val i = Intent(applicationContext, LastestActivity::class.java)

// clear all previous activity on the stack
                i.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(i)
            }
    }

}