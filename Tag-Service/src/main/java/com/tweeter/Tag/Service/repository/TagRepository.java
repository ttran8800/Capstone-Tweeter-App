package com.tweeter.Tag.Service.repository;

import com.tweeter.Tag.Service.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
