package com.secureddatahandlerspringbe.security;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends ListCrudRepository<UserData, Long> {

    UserData findByName(String name);
}
