package ru.allexs82.exceptions;

@Deprecated
public class MapsException extends Exception {
    public final ErrorCode errorCode;

    public MapsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public enum ErrorCode {
        ALL_MAPS_EXCLUDED
    }
}
