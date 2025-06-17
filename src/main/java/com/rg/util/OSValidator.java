package com.rg.util;

import org.junit.Test;

public class OSValidator {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    @Test
    public void test() {
        if (isWindows()) {
            System.out.println("This is a Windows operating system.");
        } else {
            System.out.println("This is not a Windows operating system.");
        }
    }
}
