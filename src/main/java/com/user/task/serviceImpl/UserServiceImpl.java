package com.user.task.serviceImpl;

import java.util.Objects;

import com.user.task.util.EncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.user.task.entity.User;
import com.user.task.repository.UserRepository;
import com.user.task.response.UserModel;
import com.user.task.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public String registeration(UserModel userModel ) {
		if(userModel.getUserName()==null  || Objects.equals(userModel.getUserName(), "") ){
			throw new RuntimeException("User name cannot be empty");
		}
		if(userModel.getPassword()==null  || Objects.equals(userModel.getPassword(), "")){
			throw new RuntimeException("Password cannot be empty");
		}
		if(userModel.getEmailId()==null || Objects.equals(userModel.getEmailId(), "")){
			throw new RuntimeException("Email id cannot be empty");
		}

		if(userRepository.existsByUserNameOrEmailId(userModel.getUserName(), userModel.getEmailId())){
			throw new RuntimeException("User exists already");
		}
		User user = new User();
		String encrypt = EncodingUtil.encodeString(userModel.getPassword());
		user.setPassword(encrypt);
		user.setUserName(userModel.getUserName());
		user.setEmailId(userModel.getEmailId());
		userRepository.save(user);
		return "User Successfully registered";
	}

	@Override
	public boolean loginUser(String userName, String password) {
		User user = userRepository.findByUserName(userName);
		if (user != null) {
			try {
				String decodedPassword = EncodingUtil.decodeString(user.getPassword());
				if (decodedPassword.equals(password)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error decrypting password", e);
			}
		}
		return false;
		
	}

	@Override
	public String resetPassword(String userName, String password) {
		String message=null;
		try{
			User user = userRepository.findByUserName(userName);
			if(user !=null) {
				String encryptedPaasword = EncodingUtil.encodeString(user.getPassword());
				user.setPassword(encryptedPaasword);
				userRepository.save(user);
				message="password reset successfully";
			}else{
				message="user is not present";
				throw new RuntimeException("User Not Found");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public Page<User> findByUserNameAndEmailWithPagination(String userName, String email, Pageable pageable) {
		return userRepository.findByUserNameAndEmailWithPagination(userName,email,pageable);
	}

}
