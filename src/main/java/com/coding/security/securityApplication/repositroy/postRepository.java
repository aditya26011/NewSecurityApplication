package com.coding.security.securityApplication.repositroy;

import com.coding.security.securityApplication.entity.postEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface postRepository extends JpaRepository<postEntity,Long> {
}
