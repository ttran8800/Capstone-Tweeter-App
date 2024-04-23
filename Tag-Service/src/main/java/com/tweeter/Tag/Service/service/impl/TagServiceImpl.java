package com.tweeter.Tag.Service.service.impl;

import com.tweeter.Tag.Service.entity.Tag;
import com.tweeter.Tag.Service.repository.TagRepository;
import com.tweeter.Tag.Service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
