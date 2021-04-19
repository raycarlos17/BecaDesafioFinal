package com.app.bitcoinapp.model.helper

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.app.bitcoinapp.R

class AlertDialog(message: String): DialogFragment() {

    private val dialogMessage = message
    private lateinit var listener: AlertDialogListener

    interface AlertDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(dialogMessage)
                .setPositiveButton(R.string.btn_tentar_novamente,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogPositiveClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            listener = context as AlertDialogListener
        }catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    "Não implementou AlertDialogListener"))
        }
    }
}