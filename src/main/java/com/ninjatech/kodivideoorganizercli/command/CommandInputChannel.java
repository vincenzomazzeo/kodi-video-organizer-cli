package com.ninjatech.kodivideoorganizercli.command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandInputChannel {

    private final BlockingQueue<String> queue;

    protected CommandInputChannel() {
        this.queue = new LinkedBlockingQueue<>();
    }

    protected void add(String input) {
        this.queue.add(input);
    }

    public String take() throws InterruptedException {
        return this.queue.take();
    }

}
