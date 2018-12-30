package com.pps.asciigame.common;

import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.LoginRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final Dispatcher dispatcher;
    private final ObjectOutputStream out;
    private User user;

    public Connection(final Socket socket, final Dispatcher dispatcher) throws IOException {
        this.socket = socket;
        this.dispatcher = dispatcher;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        final Thread thread = new Thread(new Reader());
        thread.start();
    }

    public boolean isReady() {
        return user != null;
    }

    public void write(final Object message) {
        if (!isReady()) {
            throw new IllegalStateException("User must be set in order to send messages.");
        }
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
                    // if the client closes stream, we'll get EOF exception instead.
                    // so better way of handling is needed - todo

                    if (message instanceof LoginRequest) {
                        user = ((LoginRequest) message).getUser();
                    } else if (isReady()) {
                        dispatcher.dispatch(message);
                    }
                }
                socket.close();
            } catch (final IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}