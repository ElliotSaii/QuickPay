package com.quickpay.commons.enums;


import lombok.Getter;

@Getter
public enum AddressEnum {

    //Cities
    MANDALAY("mandalay"), YANGON("yangon"),LASHIO("lashio"),TAUNGYI("taungyi"),

    //State
    SHAN_SATE("shan state"), KACHIN_SATE("kachin state"), CHIN_SATE("chin state"), KAYAN_SATE("kayan state"),

    //Countries
    MYANMAR("myanmar");

    private final String value;
    AddressEnum(String value) {
        this.value = value;

    }
}
