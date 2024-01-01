package org.task.exceptions;

import java.io.IOException;

public class CycledDependencyException extends IOException {

    public CycledDependencyException(String message) {
        super(message);
    }
}