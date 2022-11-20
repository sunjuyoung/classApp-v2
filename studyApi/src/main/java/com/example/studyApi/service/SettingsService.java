package com.example.studyApi.service;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.TagDTO;

import java.util.List;
import java.util.Set;

public interface SettingsService {
    List<Tag> AllTags();

    List<String> getTags(String nickname);

    Tag saveTag(TagDTO tagDTO);

    void deleteTag(TagDTO tagDTO);
}
