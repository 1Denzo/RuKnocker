package com.ruknocker.services;

import java.io.File;

public class KnockerService {
    private static final File lib = new File("src/main/resources/com.ruknocker.interop/bin/lib/knockLib.dll");

    static {
        System.load(lib.getAbsolutePath());
    }

    public native void knock(String id, short port, int protocol);
}
