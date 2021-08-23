package com.strongshop.mobile.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class PpfOption {

    @Id
    private Long id;

    private Boolean optionone; // 옵션 뭐잇는지 확인하고 수정
    private Boolean optiontwo;

}
