package com.strongshop.mobile.domain.Gallary;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Image.CompanyImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Gallary extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Company company;

    private String content;

    @OneToMany(mappedBy = "gallary",cascade = CascadeType.ALL, orphanRemoval = true) //gallaryimage 엔티티의 변경 시 자동으로 구성 내용 변경(cascade), gallary 삭제시 연관된 gallaryimage 삭제(orphanremoval)
    private List<GallaryImage> gallaryImages = new ArrayList<>();

    @Builder
    public Gallary(Company company, String content, List<GallaryImage> gallaryImages)
    {
        this.content = content;
    }

    public void updateCompany(Company company)
    {
        this.company = company;
    }

}
