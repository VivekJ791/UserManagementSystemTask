package com.user.task.service;


import com.user.task.entity.User;
import com.user.task.response.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	String registeration(UserModel userModel );

	boolean loginUser(String userName, String password);

	String resetPassword(String userName,String password);

	Page<User> findByUserNameAndEmailWithPagination(String userName, String email , Pageable pageable);

}
