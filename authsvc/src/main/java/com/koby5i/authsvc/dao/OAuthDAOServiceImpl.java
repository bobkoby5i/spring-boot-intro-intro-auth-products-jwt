package com.koby5i.authsvc.dao;

import com.koby5i.authsvc.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class OAuthDAOServiceImpl implements OAuthDAOService {

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Override
	public UserEntity getUserDetails(String emailId) {
		
		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

		System.out.println("search for emailId" + emailId);
		
		List<UserEntity> list = jdbcTemplate.query("SELECT * FROM USERS WHERE EMAIL_ID=?", new String[] { emailId },
				(ResultSet rs, int rowNum) -> {
					UserEntity user = new UserEntity();
					user.setEmailId(emailId);
					user.setId(rs.getString("ID"));
					user.setName(rs.getString("NAME"));
					user.setPassword(rs.getString("PASSWORD"));
					return user;
				});


		if(!list.isEmpty()) {

			
			UserEntity userEntity = list.get(0);

			System.out.println("userEntity (id=" + userEntity.getId() +",name=" + userEntity.getName()+").");
			
			List<String> permissionList = jdbcTemplate.query("SELECT DISTINCT p.PERMISSION_NAME \r\n" +
					"FROM PERMISSIONS p, ASSIGN_PERMISSION_TO_ROLE pr, ROLES r, ASSIGN_USER_TO_ROLE ur, USERS u \r\n" +
					"WHERE u.ID = ur.user_id \r\n" +
					"AND ur.ROLE_ID = r.ID \r\n" +
					"AND pr.ROLE_ID = ur.ROLE_ID \r\n" +
					"AND pr.PERMISSION_ID = p.ID \r\n" +
					"AND U.EMAIL_ID =?", new String[] { userEntity.getEmailId() },
					(ResultSet rs, int rowNum) -> {
						return "ROLE_" + rs.getString("PERMISSION_NAME");
					});

			System.out.println("User has roles:" + permissionList);


			if (permissionList != null && !permissionList.isEmpty()) {
				for (String permission : permissionList) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission);
					grantedAuthoritiesList.add(grantedAuthority);
					System.out.println("permission '" + permission +"' added.");
				}
				userEntity.setGrantedAuthoritiesList(grantedAuthoritiesList);
			}
			return userEntity;
		}

		System.out.println("WARNING: User not found.");
		
		return null;

	}

}
