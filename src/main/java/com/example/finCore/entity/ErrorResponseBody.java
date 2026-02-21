package com.example.finCore.entity;

import java.time.LocalDateTime;

public class ErrorResponseBody {

  private String message;
  private int statusCode;
  private LocalDateTime timeStamp;

    public ErrorResponseBody(String message, int statusCode) {
        this.message = message;
        this.statusCode=statusCode;
        this.timeStamp=LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
