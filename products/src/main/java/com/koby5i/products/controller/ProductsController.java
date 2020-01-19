package com.koby5i.products.controller;

import com.koby5i.products.model.AccessTokenMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

	@PreAuthorize("hasRole('ITEM_CREATE')")
	@RequestMapping(value="/item", method= RequestMethod.POST)
	public String createItem() {
		
		AccessTokenMapper accessTokenMapper = (AccessTokenMapper)
				((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
		
		System.out.println("ID:"+accessTokenMapper.getId());
		System.out.println("Name:"+accessTokenMapper.getName());
		System.out.println("Email ID:"+accessTokenMapper.getUserName());
		
		return "Item has been created successfully. accessToken (ID:"+accessTokenMapper.getId()+", Name:"+accessTokenMapper.getName()+", Email ID:"+accessTokenMapper.getUserName() +").";
	}
	
	@PreAuthorize("hasRole('ITEM_EDIT')")
	@RequestMapping(value="/item", method= RequestMethod.PUT)
	public String updateItem() {
		return "Item has been updated successfully";
	}
	
	@PreAuthorize("hasRole('ITEM_DELETE')")
	@RequestMapping(value="/item", method= RequestMethod.DELETE)
	public String deleteItem() {
		return "Item has been deleted successfully";
	}
	
	@PreAuthorize("hasRole('ITEM_VIEW_ALL')")
	@RequestMapping(value="/item", method= RequestMethod.GET)
	public String viewAllItem() {
		return "Item ALL API response";
	}
	
	@PreAuthorize("hasRole('ITEM_VIEW')")
	@RequestMapping(value="/itemById", method= RequestMethod.GET)
	public String viewItemByID() {
		return "Item By ID response";
	}
	
	
	
	
}
