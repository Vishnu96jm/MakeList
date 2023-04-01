package com.jmv.makelist

import android.os.Parcel
import android.os.Parcelable

class TaskList(val name: String, val tasks: ArrayList<String> = ArrayList()) : Parcelable {

    constructor(parcel : Parcel) : this(
        //first it'll find out the name of the list and we'll just unwrap those
        parcel.readString()!!,
        //and then it will get the list itself, and you have to unwrap that as well
        parcel.createStringArrayList()!!
    )

    /*
    describeContents function is used with what are known as file descriptors, and since you're
    not dealing with file descriptors, you provide a zero value.
     */
    override fun describeContents(): Int = 0

    //you are converting your task list into a parcel
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    companion object CREATOR : Parcelable.Creator<TaskList> {
        //create the parcel using the source (parcel parameter) passed into it
        /*when createFromParcel() (TaskList(parcel)) is called, constructor(parcel : Parcel) will
         be called*/
        override fun createFromParcel(parcel: Parcel): TaskList {
            return TaskList(parcel)
        }

        //it returns an array of TaskLists
        override fun newArray(size: Int): Array<TaskList?> {
            return arrayOfNulls(size)
        }
    }
}