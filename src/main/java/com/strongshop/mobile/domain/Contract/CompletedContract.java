//package com.strongshop.mobile.domain.Contract;
//
//import com.strongshop.mobile.domain.BaseEntity;
//import lombok.Builder;
//
//import javax.persistence.*;
//
//public class CompletedContract extends BaseEntity {
//
//    @Id @GeneratedValue
//    private Long id;
//
//
//    @Enumerated(EnumType.STRING)
//    private ReviewStatus reviewStatus;
//
//    @Builder
//    public CompletedContract(Long id, Contract contract, ReviewStatus reviewStatus)
//    {
//        this.id = id;
//        this.reviewStatus = reviewStatus;
//    }
//
//
//}
