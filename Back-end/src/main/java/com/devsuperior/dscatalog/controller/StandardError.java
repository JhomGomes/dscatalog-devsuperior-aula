package com.devsuperior.dscatalog.controller;

import java.time.Instant;
import java.io.Serializable;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {
    }

    /**
     * @return Instant return the timeStamp
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return Integer return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return String return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return String return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return String return the caminho
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the caminho to set
     */
    public void setPath(String path) {
        this.path = path;
    }
}