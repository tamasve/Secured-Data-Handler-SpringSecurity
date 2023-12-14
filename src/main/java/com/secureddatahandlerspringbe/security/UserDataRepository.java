package com.secureddatahandlerspringbe.security;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends ListCrudRepository<UserData, Long> {

    UserData findByUsername(String username);

    UserData findByActivation(String activation);

    UserData findByEmail(String email);
}
