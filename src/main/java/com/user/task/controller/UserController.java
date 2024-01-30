package com.user.task.controller;


import com.user.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.task.entity.User;
import com.user.task.response.UserModel;
import com.user.task.service.UserService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequestMapping("/api/user")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

	@Autowired UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<String> registerUser(@RequestBody UserModel userModel) {
		try{
			return new ResponseEntity<>(userService.registeration(userModel),HttpStatus.CREATED);
		}catch (Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public boolean loginUser(@RequestParam String userName, @RequestParam String password) {
		return userService.loginUser(userName, password);

	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestParam String userName, @RequestParam String password){
		try{
			return new ResponseEntity<>(userService.resetPassword(userName,password),HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
	}

	@GetMapping("/filterList")
	public ResponseEntity<Object> filter(@RequestParam(required = false) String userName, @RequestParam(required = false) String emailId, @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize,
										 @RequestParam(defaultValue = "ASC") Sort.Direction direction,@RequestParam(defaultValue = "id") String sortBy){
		PageRequest page = PageRequest.of(pageNumber, pageSize,direction,sortBy);
		Page<User> byUserNameAndEmail = userService.findByUserNameAndEmailWithPagination(userName, emailId, page);
		return new ResponseEntity<>(byUserNameAndEmail,HttpStatus.OK);
	}


}
