package com.joyner.aesdukpt.dukpt

import com.security.aesdukpt.KeyType

class ValuesAesDukpt {
    private lateinit var gIntermediateDerivationKeyRegister: Array<String>
    private lateinit var gIntermediateDerivationKeyInUse: BooleanArray
    private var gCurrentKey = 0
    private lateinit var gDeviceID: ByteArray
    private var gCounter: Long = 0
    private var gShiftRegister: Long = 0
    private var gDeriveKeyType: KeyType? = null
    fun getgIntermediateDerivationKeyRegister(): Array<String> {
        return gIntermediateDerivationKeyRegister
    }

    fun setgIntermediateDerivationKeyRegister(gIntermediateDerivationKeyRegister: Array<String>) {
        this.gIntermediateDerivationKeyRegister = gIntermediateDerivationKeyRegister
    }

    fun getgIntermediateDerivationKeyInUse(): BooleanArray {
        return gIntermediateDerivationKeyInUse
    }

    fun setgIntermediateDerivationKeyInUse(gIntermediateDerivationKeyInUse: BooleanArray) {
        this.gIntermediateDerivationKeyInUse = gIntermediateDerivationKeyInUse
    }

    fun getgCurrentKey(): Int {
        return gCurrentKey
    }

    fun setgCurrentKey(gCurrentKey: Int) {
        this.gCurrentKey = gCurrentKey
    }

    fun getgDeviceID(): ByteArray {
        return gDeviceID
    }

    fun setgDeviceID(gDeviceID: ByteArray) {
        this.gDeviceID = gDeviceID
    }

    fun getgCounter(): Long {
        return gCounter
    }

    fun setgCounter(gCounter: Long) {
        this.gCounter = gCounter
    }

    fun getgShiftRegister(): Long {
        return gShiftRegister
    }

    fun setgShiftRegister(gShiftRegister: Long) {
        this.gShiftRegister = gShiftRegister
    }

    fun getgDeriveKeyType(): KeyType? {
        return gDeriveKeyType
    }

    fun setgDeriveKeyType(gDeriveKeyType: KeyType?) {
        this.gDeriveKeyType = gDeriveKeyType
    }
}