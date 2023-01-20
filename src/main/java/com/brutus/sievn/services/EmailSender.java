package com.brutus.sievn.services;

public interface EmailSender {
    void send(String email, String message, String name);
}
