/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2.common

import android.app.AlertDialog
import android.content.Context

class Helper {

    companion object{
        fun alert(context: Context,title:String,message:String,btnOK: String = "OK", btnCancel: String = "Cancel"){
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnOK) {_,_ ->}
                .setNegativeButton(btnCancel) {_,_ ->}
                .show()
        }
    }

}