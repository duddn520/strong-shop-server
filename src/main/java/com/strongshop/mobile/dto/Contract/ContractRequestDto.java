package com.strongshop.mobile.dto.Contract;

import com.strongshop.mobile.domain.Contract.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ContractRequestDto {
    private Long id;
    private Long order_id;
    private Long bidding_id;
    private String detail;

    public Contract toEntity()
    {
        return Contract.builder()
                .id(id)
                .detail(detail)
                .build();
    }
}
