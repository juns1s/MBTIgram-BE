package com.Zizon.MBTInstagram.repository;

import com.Zizon.MBTInstagram.domain.UserMbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMbtiRepository extends JpaRepository<UserMbti, Long> {
    Optional<UserMbti> findById(Long userMbtiId);
    Optional<UserMbti> findBySnsAddress(String userMbtiSnsAddress);
}
