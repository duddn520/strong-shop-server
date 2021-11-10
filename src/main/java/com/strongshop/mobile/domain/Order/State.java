package com.strongshop.mobile.domain.Order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {
    BIDDING,
    BIDDING_COMPLETE,
    DESIGNATING_SHIPMENT_LOCATION,
    CAR_EXAMINATION,
    CONSTRUCTING,
    CONTSTRUCTION_COMPLETED
}
