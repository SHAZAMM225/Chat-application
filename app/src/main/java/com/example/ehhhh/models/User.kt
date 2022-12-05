package com.example.ehhhh.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid:String,val username:String,val profileimg:String):Parcelable {

    constructor():this("","","")
}