package net.xxs.common;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class GzipResponseStream extends ServletOutputStream {
	
	private OutputStream outputStream;

    public GzipResponseStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(int b) throws IOException {
    	outputStream.write(b);
    }

    public void write(byte[] b) throws IOException {
    	outputStream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
    	outputStream.write(b, off, len);
    }

}