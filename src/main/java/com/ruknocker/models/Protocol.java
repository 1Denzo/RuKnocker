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

    public static Protocol get(String protocol) {
        switch (protocol) {
            case "TCP": return TCP;
            case "UDP": return UDP;
            default: return null;
        }
    }

    @Override
    public String toString() {
        switch (protoId) {
            case 1: return "TCP";
            case 2: return "UDP";
            default: return null;
        }
    }
}
