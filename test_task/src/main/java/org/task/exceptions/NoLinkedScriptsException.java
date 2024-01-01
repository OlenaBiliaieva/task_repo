package org.task.exceptions;

import java.io.IOException;

public class NoLinkedScriptsException extends IOException {

    public NoLinkedScriptsException(String message) {
        super(message);
    }
}
