package com.leo_escobar.earthquake_monitor.api;

public class StatusWithDescription {
    private final RequestStatus status;
    private final String message;

    public StatusWithDescription(RequestStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
