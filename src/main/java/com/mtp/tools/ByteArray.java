package com.mtp.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class ByteArray extends ByteArrayOutputStream {
    public byte[] compress() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream,new Deflater(Deflater.BEST_COMPRESSION))) {
            deflaterOutputStream.write(this.toByteArray());
        }
        return byteArrayOutputStream.toByteArray();
    }
    public byte[] uncompress() throws  IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(byteArrayOutputStream)) {
            inflaterOutputStream.write(this.toByteArray());
        }
        return byteArrayOutputStream.toByteArray();
    }
}
