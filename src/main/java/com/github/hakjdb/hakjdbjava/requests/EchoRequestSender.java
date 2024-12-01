package com.github.hakjdb.hakjdbjava.requests;

public interface EchoRequestSender {
    /**
     * Sends echo request.
     *
     * @param message The message to send
     * @return The message that was received
     */
    String sendRequestEcho(String message);
}
