package com.joyner.aesdukpt.dukpt;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.joyner.aesdukpt.sp.EncriptedSharedPrefs;
import com.security.aesdukpt.AesDukpt;
import com.security.aesdukpt.KeyType;
import com.security.aesdukpt.KeyUsage;

public class ImplDukpt {

    private static ImplDukpt instance;

    private static final String KEY_VALUES_AES_DUKPT = "KEY_VALUES_AES_DUKPT";

    public static ImplDukpt getInstance() {
        if (instance == null) {
            instance = new ImplDukpt();
        }
        return instance;
    }

    /**
     * Get encripted value using DUKPT and return String if successful or null.
     * @param context The context of app.
     * @param keyAlias The String key alias value of .jks file for app signing.
     * @param dataToEncript The String value for encript.
     * @param variant Encript variant, can be EncriptVariant.DATA, EncriptVariant.PIN, EncriptVariant.MAC.
     * @return returns a EncriptedResult value or null if an error occurred.
     */
    public EncriptedResult encriptDataWithDUKPT(Context context, String keyAlias, String dataToEncript, EncriptVariant variant) {
        try {
            EncriptedSharedPrefs encriptedSharedPrefs = new EncriptedSharedPrefs.Builder(context, keyAlias, 256).build();
            String values = encriptedSharedPrefs.getStringValue(KEY_VALUES_AES_DUKPT);

            if (values == null) {
                return null;
            } else {
                ValuesAesDukpt valuesAesDukpt = new Gson().fromJson(values, ValuesAesDukpt.class);
                if (valuesAesDukpt == null) {
                    return null;
                } else {
                    AesDukpt.setgCounter(valuesAesDukpt.getgCounter());
                    AesDukpt.setgCurrentKey(valuesAesDukpt.getgCurrentKey());
                    AesDukpt.setgDeriveKeyType(valuesAesDukpt.getgDeriveKeyType());
                    AesDukpt.setgDeviceID(valuesAesDukpt.getgDeviceID());
                    AesDukpt.setgIntermediateDerivationKeyInUse(valuesAesDukpt.getgIntermediateDerivationKeyInUse());
                    AesDukpt.setgIntermediateDerivationKeyRegister(valuesAesDukpt.getgIntermediateDerivationKeyRegister());
                    AesDukpt.setgShiftRegister(valuesAesDukpt.getgShiftRegister());
                }
            }

            switch (variant) {
                case PIN:
                    //return new EncriptedResult(encriptDataVariantPin(context, keyAlias, bBdk, bKsn, dataToEncript, algoritm), ksnValue, ksnCounter);
                case DATA:
                    byte[] keyEncription = AesDukpt.generateWorkingKeys(KeyUsage._DataEncryptionEncrypt, KeyType._AES128);
                    String dataEncripted = AesDukpt.toHex(AesDukpt.encryptAes(keyEncription, AesDukpt.toByteArray(AesDukpt.paddingDataText(dataToEncript))));

                    saveCurrentValues(context, keyAlias);

                    return new EncriptedResult(dataEncripted, buildKsnForCurrentTx(), AesDukpt.getgCounter() - 1);
                case MAC:
                    //return new EncriptedResult(encriptDataVariantMac(context, keyAlias, bBdk, bKsn, dataToEncript, algoritm), ksnValue, ksnCounter);
                default:
                    return null;
            }
        } catch (Exception e) {
            Log.e("DUKPT exception: ", e.getMessage());
            return null;
        }
    }

    /**
     * Save Initial key value and return boolean if successful.
     * @param context The context of app.
     * @param keyAlias The String key alias value of .jks file for app signing.
     * @param initialKey The String value for new initial key.
     * @param keyType The String value for key type.
     * @param initialKeyID The String value for new initial key ID.
     * @return returns a boolean value.
     */
    public boolean saveInitialKey(Context context, String keyAlias, String initialKey, KType keyType, String initialKeyID) {
        try {
            byte[] ik = AesDukpt.toByteArray(initialKey);
            byte[] ikid = AesDukpt.toByteArray(initialKeyID);
            AesDukpt.loadInitialKey(ik, getKeyType(keyType), ikid);

            return saveCurrentValues(context, keyAlias);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean saveCurrentValues(Context context, String keyAlias) {
        String json = new Gson().toJson(buildValuesAesDukpt());

        EncriptedSharedPrefs encriptedSharedPrefs = new EncriptedSharedPrefs.Builder(context, keyAlias, 256).build();
        return encriptedSharedPrefs.saveStringValue(KEY_VALUES_AES_DUKPT, json);
    }

    private ValuesAesDukpt buildValuesAesDukpt() {
        ValuesAesDukpt valuesAesDukpt = new ValuesAesDukpt();
        valuesAesDukpt.setgCounter(AesDukpt.getgCounter());
        valuesAesDukpt.setgCurrentKey(AesDukpt.getgCurrentKey());
        valuesAesDukpt.setgDeriveKeyType(AesDukpt.getgDeriveKeyType());
        valuesAesDukpt.setgDeviceID(AesDukpt.getgDeviceID());
        valuesAesDukpt.setgIntermediateDerivationKeyInUse(AesDukpt.getgIntermediateDerivationKeyInUse());
        valuesAesDukpt.setgIntermediateDerivationKeyRegister(AesDukpt.getgIntermediateDerivationKeyRegister());
        valuesAesDukpt.setgShiftRegister(AesDukpt.getgShiftRegister());

        return valuesAesDukpt;
    }

    private String buildKsnForCurrentTx() {
        String longToHex = Long.toHexString(AesDukpt.getgCounter() - 1);
        String withLeadingZeros = String.format("%8s", longToHex).replace(' ', '0');
        return (AesDukpt.toHex(AesDukpt.getgDeviceID()) + withLeadingZeros).toUpperCase();
    }

    private KeyType getKeyType(KType type) {
        switch (type) {
            case _2TDEA:
                return KeyType._2TDEA;
            case _3TDEA:
                return KeyType._3TDEA;
            case AES192:
                return KeyType._AES192;
            case AES256:
                return KeyType._AES256;
            case AES128:
            default:
                return KeyType._AES128;
        }
    }
}
