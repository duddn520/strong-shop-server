package com.strongshop.mobile.domain.Company;

import com.amazonaws.services.ec2.model.transform.ProductCodeStaxUnmarshaller;
import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingHistory;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Product.Product;
import com.strongshop.mobile.domain.Review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.event.service.spi.EventListenerGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Company implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    private String name; // 회사명
    private String email;
    private String bossName; // 대표자성명
    private String phoneNumber;
    private String businessNumber; // 사업자번호
    private String fcmToken;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Bidding> biddings = new ArrayList<>();

    @OneToOne(mappedBy = "company",cascade = CascadeType.ALL)
    private CompanyInfo companyInfo;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<BiddingHistory> biddingHistories = new ArrayList<>();


    @Builder
    public Company(Long id, String name, String email, String bossName, String phoneNumber, String businessNumber, String fcmToken, List<Bidding> biddings, CompanyInfo companyInfo, List<Review> reviews, List<Product> products, List<BiddingHistory> biddingHistories) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
        this.businessNumber = businessNumber;
        this.fcmToken = fcmToken;
        this.biddings = biddings;
        this.companyInfo = companyInfo;
        this.reviews = reviews;
        this.products = products;
        this.biddingHistories = biddingHistories;
    }

    public Company updateCompany(Company company)
    {
        this.name = company.getName();
        this.bossName = company.getBossName();
        this.businessNumber = company.getBusinessNumber();

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
