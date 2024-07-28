package com.example.evalutionpractical.common.utills

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.evalutionpractical.R

class ProgressDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        if (dialog != null && dialog?.isShowing == true) {
            return
        }
        context.let { dialog = Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.loader_dialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    @SuppressLint("SuspiciousIndentation")
    fun dismiss() {
        if (dialog?.isShowing == true && dialog != null)
            dialog?.dismiss()
    }

    companion object {
        private var mInstance: ProgressDialog? = null
        @get:Synchronized
        val instance: ProgressDialog?
            get() {
                if (mInstance==null) mInstance = ProgressDialog()
                return mInstance
            }
    }
}