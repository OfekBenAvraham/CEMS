package controllers;

import entities.Request;

public interface RequestObserver {
    void update(Request request);
}
