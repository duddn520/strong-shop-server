package com.strongshop.mobile.domain.Company;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingHistory;
import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Product.Product;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.User.LoginMethod;
import com.strongshop.mobile.domain.User.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Company implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name; // 회사명
    private String email;
    private String bossName; // 대표자성명
    private String phoneNumber;
    private String businessNumber; // 사업자번호
    private String region; //지역
    private String fcmToken;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Gallery> galleries = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Bidding> biddings = new ArrayList<>();

    @OneToOne(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
    private CompanyInfo companyInfo;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BiddingHistory> biddingHistories = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<CompletedContract> completedContracts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod;

    @Transient
    private Collection<SimpleGrantedAuthority> authorities;



    @Builder
    public Company(Long id, String name, String email, String bossName, String phoneNumber, String businessNumber, String region, String fcmToken
            ,List<Gallery> galleries, List<Bidding> biddings, CompanyInfo companyInfo, List<Review> reviews, List<Product> products, List<BiddingHistory> biddingHistories,List<CompletedContract> completedContracts, LoginMethod loginMethod) {
        this.id = id;
        this.role = Role.COMPANY;
        this.name = name;
        this.email = email;
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
        this.businessNumber = businessNumber;
        this.region = region;
        this.fcmToken = fcmToken;
        this.galleries = galleries;
        this.biddings = biddings;
        this.companyInfo = companyInfo;
        this.reviews = reviews;
        this.products = products;
        this.biddingHistories = biddingHistories;
        this.completedContracts = completedContracts;
        this.loginMethod = loginMethod;
    }

    public Company updateCompany(Company company)
    {
        this.name = company.getName();
        this.bossName = company.getBossName();
        this.businessNumber = company.getBusinessNumber();

        return this;
    }

    public void updateCompanyInfo(CompanyInfo companyInfo)
    {
        this.companyInfo = companyInfo;
        companyInfo.updateCompany(this);
    }

    public void updateFcmToken(String fcmToken){this.fcmToken = fcmToken;}

    public void removeFcmToken(){this.fcmToken = null;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
