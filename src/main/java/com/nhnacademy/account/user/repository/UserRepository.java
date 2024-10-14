package com.nhnacademy.account.user.repository;

import com.nhnacademy.account.user.entity.Status;
import com.nhnacademy.account.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


//db와 연결되는 지점 -> 쿼리작성(jpa가 해줌)
 public interface UserRepository extends JpaRepository<User, String> {


    //select * from User where id =? and pwd = ?
    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.pwd = :pwd")
    User findByInfoByIdandPwd(String userId, String pwd);


    @Query("SELECT u FROM User u WHERE u.status = :status")
    List<User> findALLByStatus(Status status);

   @Query("SELECT u FROM User u WHERE u.id = :userId AND u.status = :status")
   Optional<User> findByIdAndStatus(String userId, Status status);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
   Optional<User> findById(String userId);

}
