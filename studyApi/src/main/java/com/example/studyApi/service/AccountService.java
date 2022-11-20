package com.example.studyApi.service;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {

    AccountDTO getAccount(Long id);

    AccountDTO getAccountByNickname(String nickname);

    String saveUser(SignUpDTO signUpDTO);

    boolean confirmEmailToken(String email, String token);

    boolean updateProfile(ProfileDTO profileDTO, String nickname);

    ProfileDTO getProfileByNickname(String nickname);

    void updatePassword(PasswordDTO passwordDTO, String nickname);

    void addTag(String nickname, Tag tag);

    void deleteTag(String tagTitle, String nickname);

    List<String> getZone(String nickname);

    void addZone(List<ZoneDTO> zoneData,String nickname);
}
