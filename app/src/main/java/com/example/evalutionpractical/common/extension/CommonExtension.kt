package com.example.evalutionpractical.common.extension

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.evalutionpractical.Model.Temp
import com.example.evalutionpractical.common.utills.ProgressDialog
import com.google.gson.Gson
 
 inline fun < reified T:Fragment> FragmentManager.createAndShowBottomSheetFragmentWithListener(
    bundle: Bundle? = null,
    listenerSetup:(T) ->Unit
){
    val fragInfo = T::class.java.getDeclaredConstructor().newInstance()
    val tag = T::class.java.simpleName
    bundle.let {
        fragInfo.arguments = bundle
    }
    listenerSetup.invoke(fragInfo as T)
    this.beginTransaction().add(fragInfo,tag).commit()
}

fun hideProgressDialog() {
    ProgressDialog.instance?.dismiss()
}
fun Context.showProgressDialog(){
    ProgressDialog.instance?.show(this)
}

 fun fromJsonStringToJsonObject(jsonString: String): Temp {
    return Gson().fromJson(jsonString, Temp::class.java)
}

 fun fromJsonObjectToJsonString(it: Temp): String {
    return Gson().toJson(it)
}