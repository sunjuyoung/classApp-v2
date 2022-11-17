package com.example.studyApi.service;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService{

    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<Tag> getTags() {
        return null;
    }

    @Override
    public void saveTag(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);

    }
}
