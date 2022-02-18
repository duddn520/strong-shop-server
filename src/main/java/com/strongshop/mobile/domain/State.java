package com.strongshop.mobile.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {
    BIDDING,
    BIDDING_COMPLETE,
    DESIGNATING_SHIPMENT_LOCATION,
    CAR_EXAMINATION,
    CAR_EXAMINATION_FIN,
    PRE_CONSTRUCTING,
    CONSTRUCTING,
    CONSTRUCTION_COMPLETED

}
