package com.koby5i.authsvcmongo.repositories;

import com.koby5i.authsvcmongo.model.AppUser;
import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, String> {
}
