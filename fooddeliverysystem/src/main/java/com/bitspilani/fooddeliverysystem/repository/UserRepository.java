package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndRole(String username, UserRole userRole);

    // Find users who have been active within the last 'n' minutes
    @Query("SELECT u FROM User u WHERE u.lastLogin >= :activeSince")
    List<User> findActiveUsersWithinLastMinutes(LocalDateTime activeSince);

}
