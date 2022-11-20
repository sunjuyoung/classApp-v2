package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService{

    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<Tag> AllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<String> getTags(String nickname) {
        Account account = accountRepository.findAccountByNickname(nickname);
        if(account.getTags() != null){
            List<String> tags =  account.getTags().stream().map(Tag::getTitle).collect(Collectors.toList());
            return tags;
        }
        return null;
    }

    @Override
    public Tag saveTag(TagDTO tagDTO) {
        Optional<Tag> tag = tagRepository.findByTitle(tagDTO.getTitle());
        if(!tag.isPresent()){
            Tag newTag = modelMapper.map(tagDTO, Tag.class);
            tagRepository.save(newTag);
            return newTag;
        }
        return null;

    }

    @Override
    public void deleteTag(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        tagRepository.delete(tag);

    }
}
