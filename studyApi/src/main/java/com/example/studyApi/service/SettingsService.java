package com.example.studyApi.service;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.TagDTO;

import java.util.Set;

public interface SettingsService {
    Set<Tag> getTags();

    void saveTag(TagDTO tagDTO);
}
