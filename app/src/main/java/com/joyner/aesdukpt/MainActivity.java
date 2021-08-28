package com.joyner.aesdukpt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.joyner.aesdukpt.dukpt.EncriptVariant;
import com.joyner.aesdukpt.dukpt.EncriptedResult;
import com.joyner.aesdukpt.dukpt.ImplDukpt;
import com.joyner.aesdukpt.dukpt.KType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickGetDukptEncriptedValue(View view) {
        ImplDukpt implDukpt = ImplDukpt.getInstance();
        boolean result = implDukpt.saveInitialKey(this, "test", "1273671EA26AC29AFA4D1084127652A1", KType.AES128, "1234567890123456");
        Log.d("CREATED INITIAL KEY", result ? "SUCCESS" : "ERROR");

        EncriptedResult encriptedResult = implDukpt.encriptDataWithDUKPT(this, "test", "A912150391AB65A67E52883D81CE2D15", EncriptVariant.PIN, KType.AES128);
        if (encriptedResult != null) {
            Log.d("DATA ENCRYPTED", encriptedResult.getDataEncripted());
            Log.d("KSN VALUE", encriptedResult.getKsnUsed());
        }

        EncriptedResult encriptedResult2 = implDukpt.encriptDataWithDUKPT(this, "test", "1234567890", EncriptVariant.DATA, KType.AES128);
        if (encriptedResult2 != null) {
            Log.d("DATA ENCRYPTED", encriptedResult2.getDataEncripted());
            Log.d("KSN VALUE", encriptedResult2.getKsnUsed());
        }
    }
}