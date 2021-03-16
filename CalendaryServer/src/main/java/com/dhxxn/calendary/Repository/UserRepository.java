package com.dhxxn.calendary.Repository;

import com.dhxxn.calendary.DTO.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
