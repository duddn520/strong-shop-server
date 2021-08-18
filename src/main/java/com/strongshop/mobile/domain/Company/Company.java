package com.strongshop.mobile.domain.Company;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 회사명
    private String bossName; // 대표자성명
    private String address; // 주소
    private String detailAddress; // 상세주소
    private String contact; // 연락처
    private String businessNumber; // 사업자번호

    @Builder
    public Company(Long id, String name, String bossName, String address, String detailAddress, String contact, String businessNumber) {
        this.id = id;
        this.name = name;
        this.bossName = bossName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.contact = contact;
        this.businessNumber = businessNumber;
    }
}
