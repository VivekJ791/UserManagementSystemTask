package com.user.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.user.task.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String userName);

	boolean existsByUserNameOrEmailId(String userName,String emailId);

	@Query("select u from User u where  u.userName like concat('%',:userName,'%') and u.emailId like concat('%',:email ,'%') ")
	Page<User> findByUserNameAndEmailWithPagination(String userName,String email ,Pageable pageable);


}
