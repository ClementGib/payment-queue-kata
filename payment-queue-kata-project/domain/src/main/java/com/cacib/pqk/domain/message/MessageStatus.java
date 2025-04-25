package com.cacib.pqk.domain.message;

public enum MessageStatus {
    RECEIVED,
    IN_PROGRESS,
    PROCESSED,
    ERROR,
    RETRYING,
    UNREACHABLE,
    TIMEOUT,
    IGNORED
}