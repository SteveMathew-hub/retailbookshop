package com.retail.retailbookshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.retail.retailbookshop.dto.RoleDTO;
import com.retail.retailbookshop.dto.UserDTO;
import com.retail.retailbookshop.document.User;
import com.retail.retailbookshop.document.Role;
//import com.retail.retailbookshop.entity.Role;
//import com.retail.retailbookshop.entity.User;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Override
	public String addUser(UserDTO userDTO) throws RetailBookShopException {

		String addedUserEmail = null;

		boolean emailAvailable = userRepository.findByEmail(userDTO.getEmail()).isEmpty();

		if (emailAvailable) {
			User user = new User();
			user.setEmail(userDTO.getEmail());
			user.setPassword(userDTO.getPassword());
			user.setName(userDTO.getName());
			user.setAddress(userDTO.getAddress());

			List<Role> roles = new ArrayList<>();
			for (RoleDTO roleDTO : userDTO.getRoles()) {
				Role role = new Role();
				role.setRole(roleDTO.getRole());
				roles.add(role);
			}

			user.setRoles(roles);

			User addedUser = userRepository.save(user);
			addedUserEmail = addedUser.getEmail();

		} else {
			throw new RetailBookShopException("UserService.EMAIL_IN_USE");
		}

		return addedUserEmail;

	}

	@Override
	public UserDTO getUserByEmail(String email) throws RetailBookShopException {

		Optional<User> optional = userRepository.findByEmail(email);
		User user = optional.orElseThrow(() -> new RetailBookShopException("UserService.USER_NOT_FOUND"));

		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());

		List<RoleDTO> roleDTOs = new ArrayList<>();

		for (Role role : user.getRoles()) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setRole(role.getRole());
			roleDTOs.add(roleDTO);
		}

		userDTO.setRoles(roleDTOs);

		userDTO.setAddress(user.getAddress());

		return userDTO;
	}

	@Override
	public UserDTO authenticateUser(String email, String password, String urole) throws RetailBookShopException {
		Optional<User> optional = userRepository.findByEmail(email);
		User user = optional.orElseThrow(() -> new RetailBookShopException("UserService.USER_NOT_FOUND"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RetailBookShopException("UserService.INVALID_CREDENTIALS");
		}

		List<String> userRole = user.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList());
		if (!userRole.contains(urole)) {
			throw new RetailBookShopException("UserService.USER_DONT_HAVE_ACCESS");
		}

		UserDTO userDTO = new UserDTO();

		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());

		List<RoleDTO> roleDTOs = new ArrayList<>();

		for (Role role : user.getRoles()) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setRole(role.getRole());
			roleDTOs.add(roleDTO);
		}
		userDTO.setRoles(roleDTOs);

		userDTO.setAddress(user.getAddress());

		return userDTO;
	}

}
