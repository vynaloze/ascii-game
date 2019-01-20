package com.pps.asciigame.common;

import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.Login;
import com.pps.asciigame.common.protocol.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
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
            LOGGER.error(e);
        }
    }

    private class Reader implements Runnable {
        @Override
        public void run() {
            try (final ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                Message message;
                while ((message = (Message) in.readObject()) != null) {   // it will never happen (until client explicitly writes null).
                    // if the client closes stream, we'll get EOF exception instead.
                    // so better way of handling is needed - todo

                    if (message instanceof Login) { //fixme - this handling should be better
                        user = message.getUser();
                        dispatcher.dispatch(message);
                    } else if (isReady()) {
                        if (message.getUser().equals(user) || message.isBroadcast()) {
                            dispatcher.dispatch(message);
                        } else {
                            throw new RuntimeException("Some filthy hacker tries to impersonate someone else!"); //fixme
                        }
                    }
                }
                socket.close();
            } catch (final IOException | ClassNotFoundException e) {
                LOGGER.error(e);
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