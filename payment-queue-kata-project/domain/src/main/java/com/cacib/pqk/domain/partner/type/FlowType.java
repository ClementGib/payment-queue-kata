package com.cacib.pqk.domain.partner.type;

import lombok.Getter;

@Getter
public enum FlowType {

    MESSAGE('M'),
    ALERTING('A'),
    NOTIFICATION('N');

    public static final char STR_MESSAGE = 'M';
    public static final char STR_ALERTING = 'A';
    public static final char STR_NOTIFICATION = 'N';
    private final char flowTypeCode;

    FlowType(Character flowTypeCode) {
        this.flowTypeCode = flowTypeCode;
    }

}
