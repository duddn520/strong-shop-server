package com.strongshop.mobile.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String userName);
}
