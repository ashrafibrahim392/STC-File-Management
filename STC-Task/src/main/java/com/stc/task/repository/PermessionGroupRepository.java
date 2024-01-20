package com.stc.task.repository;

import com.stc.task.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PermessionGroupRepository extends JpaRepository<PermissionGroup, Long>, PagingAndSortingRepository<PermissionGroup, Long> {
}
