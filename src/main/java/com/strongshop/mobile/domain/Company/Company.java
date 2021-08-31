package com.strongshop.mobile.domain.Company;

import com.strongshop.mobile.domain.Bidding.Bidding;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "company")
    private List<Bidding> biddings;

    @Builder
    public Company(Long id, String name, String bossName, String address, String detailAddress, String contact, String businessNumber, List<Bidding> biddings) {
        this.id = id;
        this.name = name;
        this.bossName = bossName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.contact = contact;
        this.businessNumber = businessNumber;
        this.biddings = biddings;
    }

    public Company updateCompany(Company company)
    {
        this.name = company.getName();
        this.bossName = company.getBossName();
        this.address = company.getAddress();
        this.detailAddress = company.getDetailAddress();
        this.contact = company.getContact();
        this.businessNumber = company.getBusinessNumber();

        return this;
    }
}
