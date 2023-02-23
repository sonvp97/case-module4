package com.codegym.model.repository;

import com.codegym.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT r.name FROM roles r" +
                    "INNER JOIN users u ON r.id = u.role_id " +
                    "WHERE u.id = (:userId)")
    List<String> findRoleNamesByUserId(@Param("userId") Long userId);
}
