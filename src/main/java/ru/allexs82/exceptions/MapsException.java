package ru.allexs82.exceptions;

public class MapsException extends Exception {
    public final ErrorCode errorCode;

    public MapsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public enum ErrorCode {
        ALL_MAPS_EXCLUDED
    }
}
