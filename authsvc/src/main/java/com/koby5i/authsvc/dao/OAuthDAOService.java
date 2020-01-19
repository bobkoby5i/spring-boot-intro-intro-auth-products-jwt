package com.koby5i.authsvc.dao;

import com.koby5i.authsvc.model.UserEntity;

public interface OAuthDAOService  {

	public UserEntity getUserDetails(String emailId);
}
