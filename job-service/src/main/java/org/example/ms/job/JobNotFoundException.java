package org.example.ms.job;

public class JobNotFoundException extends Throwable {
    public JobNotFoundException(String message) {
        super(message);
    }
}
