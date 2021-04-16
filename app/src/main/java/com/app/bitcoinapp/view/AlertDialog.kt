package com.app.bitcoinapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.app.bitcoinapp.R

class AlertDialog(message: String): DialogFragment() {

    private val dialogMessage = message

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(dialogMessage)
                .setPositiveButton(R.string.btn_tentar_novamente,
                    DialogInterface.OnClickListener { dialog, id ->
                        // A fazer (Recarregar)
                    })
                .setNegativeButton(R.string.btn_cancelar,
                    DialogInterface.OnClickListener { dialog, id ->
                        // A fazer (Cancelar)
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}