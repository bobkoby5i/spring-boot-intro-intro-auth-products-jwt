package com.koby5i.authsvcmongo.service;

import com.koby5i.authsvcmongo.model.AppUser;
import com.koby5i.authsvcmongo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    @Override
    public List<AppUser> listAll() {
        List<AppUser> appUsers = new ArrayList<>();
        appUserRepository.findAll().forEach(appUsers::add); //fun with Java 8
        return appUsers;
    }

    @Override
    public AppUser getById(String id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public AppUser saveOrUpdate(AppUser appUser) {
        appUserRepository.save(appUser);
        //ObjectId.get()
        return appUser;
    }

    @Override
    public void delete(String id) {
        appUserRepository.deleteById(id);
    }


}
