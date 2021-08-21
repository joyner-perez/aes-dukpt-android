package com.joyner.aesdukpt.dukpt;

public class EncriptedResult {
    private final String dataEncripted;
    private final String ksnUsed;
    private final long counter;

    public EncriptedResult(String dataEncripted, String ksnUsed, long counter) {
        this.dataEncripted = dataEncripted;
        this.ksnUsed = ksnUsed;
        this.counter = counter;
    }

    public String getDataEncripted() {
        return dataEncripted;
    }

    public String getKsnUsed() {
        return ksnUsed;
    }

    public long getCounter() {
        return counter;
    }
}
