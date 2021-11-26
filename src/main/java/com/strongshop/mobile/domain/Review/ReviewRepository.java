package com.strongshop.mobile.domain.Review;

import com.strongshop.mobile.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.UnknownServiceException;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Optional<List<Review>> findAllByCompanyIdOrderByCreatedTimeDesc(Long companyId);
    Optional<List<Review>> findAllByUser(User user);
}
