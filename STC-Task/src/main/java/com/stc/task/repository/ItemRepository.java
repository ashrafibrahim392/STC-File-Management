package com.stc.task.repository;

import com.stc.task.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {

    List<Item> findByParentId(Long parentId);
}
