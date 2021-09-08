package com.joyner.aesdukpt.dukpt

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.joyner.aesdukpt.sp.EncriptedSharedPrefs
import com.security.aesdukpt.AesDukpt
import com.security.aesdukpt.KeyType
import com.security.aesdukpt.KeyUsage
import java.util.*

class ImplDukpt {
    /**
     * Get encripted value using DUKPT and return String if successful or null.
     * @param context The context of app.
     * @param keyAlias The String key alias value of .jks file for app signing.
     * @param dataToEncript The String value for encript.
     * @param variant Encript variant, can be EncriptVariant.DATA, EncriptVariant.PIN, EncriptVariant.MAC.
     * @return returns a EncriptedResult value or null if an error occurred.
     */
    fun encriptDataWithDUKPT(context: Context, keyAlias: String, dataToEncript: String?, variant: EncriptVariant?, kType: KType): EncriptedResult? {
        return try {
            val encriptedSharedPrefs = EncriptedSharedPrefs.Builder(context, keyAlias, 256).build()
            val values = encriptedSharedPrefs.getStringValue(KEY_VALUES_AES_DUKPT)
            if (values == null) {
                return null
            } else {
                val valuesAesDukpt = Gson().fromJson(values, ValuesAesDukpt::class.java)
                if (valuesAesDukpt == null) {
                    return null
                } else {
                    AesDukpt.setgCounter(valuesAesDukpt.getgCounter())
                    AesDukpt.setgCurrentKey(valuesAesDukpt.getgCurrentKey())
                    AesDukpt.setgDeriveKeyType(valuesAesDukpt.getgDeriveKeyType())
                    AesDukpt.setgDeviceID(valuesAesDukpt.getgDeviceID())
                    AesDukpt.setgIntermediateDerivationKeyInUse(valuesAesDukpt.getgIntermediateDerivationKeyInUse())
                    AesDukpt.setgIntermediateDerivationKeyRegister(valuesAesDukpt.getgIntermediateDerivationKeyRegister())
                    AesDukpt.setgShiftRegister(valuesAesDukpt.getgShiftRegister())
                }
            }
            var dataEncripted: String? = null
            val keyEncription: ByteArray
            when (variant) {
                EncriptVariant.PIN -> {
                    keyEncription = AesDukpt.generateWorkingKeys(KeyUsage._PINEncryption, getKeyType(kType))
                    dataEncripted = AesDukpt.toHex(AesDukpt.encryptAes(keyEncription, AesDukpt.toByteArray(AesDukpt.paddingDataText(dataToEncript))))
                }
                EncriptVariant.DATA -> {
                    keyEncription = AesDukpt.generateWorkingKeys(KeyUsage._DataEncryptionEncrypt, getKeyType(kType))
                    dataEncripted = AesDukpt.toHex(AesDukpt.encryptAes(keyEncription, AesDukpt.toByteArray(AesDukpt.paddingDataText(dataToEncript))))
                }
                else -> {
                }
            }
            if (dataEncripted == null) {
                null
            } else {
                saveCurrentValues(context, keyAlias)
                EncriptedResult(dataEncripted, buildKsnForCurrentTx(), AesDukpt.getgCounter() - 1)
            }
        } catch (e: Exception) {
            Log.e("DUKPT exception: ", e.message!!)
            null
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
    fun saveInitialKey(context: Context, keyAlias: String, initialKey: String?, keyType: KType, initialKeyID: String?): Boolean {
        return try {
            val ik = AesDukpt.toByteArray(initialKey)
            val ikid = AesDukpt.toByteArray(initialKeyID)
            AesDukpt.loadInitialKey(ik, getKeyType(keyType), ikid)
            saveCurrentValues(context, keyAlias)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Save Initial key value and return boolean if successful.
     * @param context The context of app.
     * @param keyAlias The String key alias value of .jks file for app signing.
     * @return returns a boolean value.
     */
    fun isInitializedDukpt(context: Context?, keyAlias: String?): Boolean {
        return try {
            val encriptedSharedPrefs = context?.let { keyAlias?.let { it1 -> EncriptedSharedPrefs.Builder(it, it1, 256).build() } }
            val values = encriptedSharedPrefs?.getStringValue(KEY_VALUES_AES_DUKPT)
            if (values == null) {
                false
            } else {
                val valuesAesDukpt = Gson().fromJson(values, ValuesAesDukpt::class.java)
                valuesAesDukpt != null
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun saveCurrentValues(context: Context, keyAlias: String): Boolean {
        val json = Gson().toJson(buildValuesAesDukpt())
        val encriptedSharedPrefs = EncriptedSharedPrefs.Builder(context, keyAlias, 256).build()
        return encriptedSharedPrefs.saveStringValue(KEY_VALUES_AES_DUKPT, json)
    }

    private fun buildValuesAesDukpt(): ValuesAesDukpt {
        val valuesAesDukpt = ValuesAesDukpt()
        valuesAesDukpt.setgCounter(AesDukpt.getgCounter())
        valuesAesDukpt.setgCurrentKey(AesDukpt.getgCurrentKey())
        valuesAesDukpt.setgDeriveKeyType(AesDukpt.getgDeriveKeyType())
        valuesAesDukpt.setgDeviceID(AesDukpt.getgDeviceID())
        valuesAesDukpt.setgIntermediateDerivationKeyInUse(AesDukpt.getgIntermediateDerivationKeyInUse())
        valuesAesDukpt.setgIntermediateDerivationKeyRegister(AesDukpt.getgIntermediateDerivationKeyRegister())
        valuesAesDukpt.setgShiftRegister(AesDukpt.getgShiftRegister())
        return valuesAesDukpt
    }

    private fun buildKsnForCurrentTx(): String {
        val longToHex = java.lang.Long.toHexString(AesDukpt.getgCounter() - 1)
        val withLeadingZeros = String.format("%8s", longToHex).replace(' ', '0')
        return (AesDukpt.toHex(AesDukpt.getgDeviceID()) + withLeadingZeros).uppercase(Locale.getDefault())
    }

    private fun getKeyType(type: KType): KeyType {
        return when (type) {
            KType._2TDEA -> KeyType._2TDEA
            KType._3TDEA -> KeyType._3TDEA
            KType.AES192 -> KeyType._AES192
            KType.AES256 -> KeyType._AES256
            KType.AES128 -> KeyType._AES128
        }
    }

    companion object {
        @JvmStatic
        var instance: ImplDukpt? = null
            get() {
                if (field == null) {
                    field = ImplDukpt()
                }
                return field
            }
            private set
        private const val KEY_VALUES_AES_DUKPT = "KEY_VALUES_AES_DUKPT"
    }
}