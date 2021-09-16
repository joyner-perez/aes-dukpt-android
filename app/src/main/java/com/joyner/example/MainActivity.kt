package com.joyner.example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joyner.aesdukpt.dukpt.EncriptVariant
import com.joyner.aesdukpt.dukpt.ImplDukpt.Companion.instance
import com.joyner.aesdukpt.dukpt.KType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.buttonTest)
        button.setOnClickListener{
            onClickGetDukptEncriptedValue()
        }
    }

    fun onClickGetDukptEncriptedValue() {
        val implDukpt = instance
        val result = implDukpt!!.saveInitialKey(this, "test", "1273671EA26AC29AFA4D1084127652A1", KType.AES128, "1234567890123456")
        Log.d("CREATED INITIAL KEY", if (result) "SUCCESS" else "ERROR")

        val encriptedResult = implDukpt.encriptDataWithDUKPT(this, "test", "A912150391AB65A67E52883D81CE2D15", EncriptVariant.PIN, KType.AES128)
        if (encriptedResult != null) {
            Log.d("DATA ENCRYPTED", encriptedResult.dataEncripted)
            Log.d("KSN VALUE", encriptedResult.ksnUsed)
            Toast.makeText(this, encriptedResult.dataEncripted, Toast.LENGTH_SHORT).show()
        }

        val encriptedResult2 = implDukpt.encriptDataWithDUKPT(this, "test", "1234567890", EncriptVariant.DATA, KType.AES128)
        if (encriptedResult2 != null) {
            Log.d("DATA ENCRYPTED", encriptedResult2.dataEncripted)
            Log.d("KSN VALUE", encriptedResult2.ksnUsed)
        }

        Log.d("DUKPT INITIALIZED", implDukpt.isInitializedDukpt(this, "test").toString())
    }
}