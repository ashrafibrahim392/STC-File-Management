package com.stc.task.repository;

import com.stc.task.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileRepository extends JpaRepository<File, Long>, PagingAndSortingRepository<File, Long> {

}


