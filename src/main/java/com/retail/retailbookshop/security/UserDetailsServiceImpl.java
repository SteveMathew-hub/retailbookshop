package com.retail.retailbookshop.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retail.retailbookshop.document.User;
//import com.retail.retailbookshop.entity.User;
import com.retail.retailbookshop.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByEmail(username);
		User user = optional.orElseThrow(() -> new UsernameNotFoundException("UserDetailsImpl.USER_NOT_FOUND"));

		return new UserDetailsImpl(user);
	}

}
