package com.koby5i.authsvcmongo.controllers;

import com.koby5i.authsvcmongo.model.AppUser;
import com.koby5i.authsvcmongo.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppUsersController {
    private AppUserService appUserService;

    @Autowired
    public void setProductService(AppUserService productService) {
        this.appUserService = productService;
    }

    //@PreAuthorize("hasRole('PRODUCT_VIEW_ALL')")
    @RequestMapping(value = "/api/appUsers", method = RequestMethod.GET)
    public Iterable<AppUser> getAllProducts(){
        System.out.println("GET /api/appUsers");
        return appUserService.listAll();
    }

    //@PreAuthorize("hasRole('PRODUCT_VIEW_ALL')")
    @RequestMapping(value = "/api/appUser/{id}", method = RequestMethod.GET)
    public AppUser getAppUser(@PathVariable String id){
        System.out.println("GET /api/appUser/{id} id=" + id);
        AppUser appUser = appUserService.getById(id);
        //product.getId().toHexString()
        //System.out.println("Fetch Product Id: " + product.getId());
        //System.out.println("Fetch Product Id: " + product.getId().toHexString());
        return appUser;
    }

}
