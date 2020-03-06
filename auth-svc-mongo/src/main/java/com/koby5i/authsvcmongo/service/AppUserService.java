package com.koby5i.authsvcmongo.service;

import com.koby5i.authsvcmongo.model.AppUser;


import java.util.List;


public interface AppUserService {

    List<AppUser> listAll();

    AppUser getById(String id);

    AppUser saveOrUpdate(AppUser appUser);

    void delete(String id);

}
