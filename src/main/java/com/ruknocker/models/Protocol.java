package com.ruknocker.models;

public enum Protocol {
    TCP(1),
    UDP(2);

    private int protoId;

    Protocol(int protoId) {
        this.protoId = protoId;
    }

    public int getProtocol() {
        return protoId;
    }
}
