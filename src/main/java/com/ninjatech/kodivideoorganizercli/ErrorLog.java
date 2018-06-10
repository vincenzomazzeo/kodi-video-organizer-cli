package com.ninjatech.kodivideoorganizercli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ErrorLog extends OutputStream {

    private final ByteArrayOutputStream buffer;

    public ErrorLog() {
        this.buffer = new ByteArrayOutputStream((int) Math.pow(2, 17));
    }

    @Override
    public void write(int b) throws IOException {
        this.buffer.write(b);
    }

    public String getAndResetBuffer() {
        String result = new String(this.buffer.toByteArray(), StandardCharsets.UTF_8);
        this.buffer.reset();
        return result;
    }

}
