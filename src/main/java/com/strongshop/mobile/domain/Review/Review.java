package com.strongshop.mobile.domain.Review;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Review extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL)
    private List<ReviewImageUrl> reviewImageUrls = new ArrayList<>();

    private String content;
    private float rating;
    private String reply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Review(Long id,Company company, String content, float rating, List<ReviewImageUrl> reviewImageUrls,String reply,User user)
    {
        this.id = id;
        this.company = company;
        this.content = content;
        this.rating = rating;
        this.reviewImageUrls = reviewImageUrls;
        this.reply = reply;
        this.user = user;


    }

    public void updateReviewImageUrls(List<ReviewImageUrl> imageUrls )
    {
        this.reviewImageUrls = imageUrls;
    }

    public void updateReviewIdToUrls(List<ReviewImageUrl> imageUrls)
    {
        for(ReviewImageUrl img : imageUrls){
            img.updateReviewId(this);
        }
    }

    public void updateReply(String reply)
    {
        this.reply = reply;
    }

    public void updateUser(User user) { this.user = user;}

}
