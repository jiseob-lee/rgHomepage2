package com.rg.typing.dto;

import java.io.File;

public class Pair implements Comparable<Object> {
    public long t;
    public File f;

    public Pair(File file) {
        f = file;
        t = file.lastModified();
    }

    public int compareTo(Object o) {
        long u = ((Pair) o).t;
        return t < u ? -1 : t == u ? 0 : 1;
    }
}
