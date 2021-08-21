package com.joyner.aesdukpt.dukpt;

import com.security.aesdukpt.KeyType;

public class ValuesAesDukpt {
    private String[] gIntermediateDerivationKeyRegister;
    private boolean[] gIntermediateDerivationKeyInUse;
    private int gCurrentKey;
    private byte[] gDeviceID;
    private long gCounter;
    private long gShiftRegister;
    private KeyType gDeriveKeyType;

    public String[] getgIntermediateDerivationKeyRegister() {
        return gIntermediateDerivationKeyRegister;
    }

    public void setgIntermediateDerivationKeyRegister(String[] gIntermediateDerivationKeyRegister) {
        this.gIntermediateDerivationKeyRegister = gIntermediateDerivationKeyRegister;
    }

    public boolean[] getgIntermediateDerivationKeyInUse() {
        return gIntermediateDerivationKeyInUse;
    }

    public void setgIntermediateDerivationKeyInUse(boolean[] gIntermediateDerivationKeyInUse) {
        this.gIntermediateDerivationKeyInUse = gIntermediateDerivationKeyInUse;
    }

    public int getgCurrentKey() {
        return gCurrentKey;
    }

    public void setgCurrentKey(int gCurrentKey) {
        this.gCurrentKey = gCurrentKey;
    }

    public byte[] getgDeviceID() {
        return gDeviceID;
    }

    public void setgDeviceID(byte[] gDeviceID) {
        this.gDeviceID = gDeviceID;
    }

    public long getgCounter() {
        return gCounter;
    }

    public void setgCounter(long gCounter) {
        this.gCounter = gCounter;
    }

    public long getgShiftRegister() {
        return gShiftRegister;
    }

    public void setgShiftRegister(long gShiftRegister) {
        this.gShiftRegister = gShiftRegister;
    }

    public KeyType getgDeriveKeyType() {
        return gDeriveKeyType;
    }

    public void setgDeriveKeyType(KeyType gDeriveKeyType) {
        this.gDeriveKeyType = gDeriveKeyType;
    }
}
