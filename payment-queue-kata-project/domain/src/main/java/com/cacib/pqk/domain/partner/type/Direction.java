package com.cacib.pqk.domain.partner.type;

import lombok.Getter;

@Getter
public enum Direction {
    INBOUND('I'),
    OUTBOUND('O');

    public static final char STR_INBOUND = 'I';
    public static final char STR_OUTBOUND = 'O';
    private final char directionCode;

    Direction(Character directionCode) {
        this.directionCode = directionCode;
    }
}
