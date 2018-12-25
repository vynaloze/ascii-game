package com.pps.asciigame.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final Dispatcher dispatcher;
    private final ObjectOutputStream out;
    //todo some client identifier

    public Connection(final Socket socket, final Dispatcher dispatcher) throws IOException {
        this.socket = socket;
        this.dispatcher = dispatcher;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        final Thread thread = new Thread(new Reader());
        thread.start();
    }

    public void write(final Object message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Reader implements Runnable {
        @Override
        public void run() {
            try (final ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                Object message;
                while ((message = in.readObject()) != null) {   // it will never happen (until client explicitly writes null).
                    dispatcher.dispatch(message);               // if the client closes stream, we'll get EOF exception instead.
                }                                               // so better way of handling is needed - todo
                socket.close();
            } catch (final IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}