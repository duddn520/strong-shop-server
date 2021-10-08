package com.strongshop.mobile.domain.Car;

import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Car {

    @Id
    private Long id;

    //유저 매핑필요
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user")
    private User user;

    private String carNo;

    @Builder
    public Car(Long id, User user, String carNo) {
        this.id = id;
        this.user = user;
        this.carNo = carNo;
    }

//
//    // 차번호
//    private String carNo;
//    // 제조사
//    private String manufacturer;
//    // 차종 ( 세단, SUV)
//    private String type;
//    // 모델명
//    private String model;
//    // 상세 모델명
//    private String detailModel;
//    // 등급
//    private String rating;
//    // 상세 등급
//    private String detailRating;
//    // 변속기 (오토, 수동, 기타 ...)
//    private String gearbox;
//    // 연료 (가솔린, 디젤, LPG ...)
//    private String fuel;
//    //색상
//    private String color;
//    // 연식
//    private Integer modelYear;
//    // 주행거리
//    private Integer mileage;
//    // 출고가
//    private Integer price;
//    // 최초등록일
//    private Integer initialRegistration;
//    // 사고이력
//    @Enumerated(EnumType.STRING)
//    private AccidentHistory history; //
//    // 소유
//    private String owner;
//    // 기타이력
//    private String historyEtc; // TODO - enum으로 수정


}
