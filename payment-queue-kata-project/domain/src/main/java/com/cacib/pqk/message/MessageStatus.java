package com.cacib.pqk.message;

public enum MessageStatus {
    RECEIVED,
    IN_PROGRESS,
    PROCESSED,
    SUCCESS,
    ERROR,
    RETRYING,
    INVALID,
    DEAD_LETTERED,
    TIMEOUT,
    IGNORED,
    WAITING_ACK
}