package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Roles;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.example.studyApi.dto.*;
import com.example.studyApi.dto.file.ProfileImageDTO;
import com.example.studyApi.mail.EmailMessage;
import com.example.studyApi.mail.EmailService;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.TagRepository;
import com.example.studyApi.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final TagRepository tagRepository;
    private final ZoneRepository zoneRepository;

    @Value("${com.example.studyApi.path}")
    private String uploadPath;

    @Override
    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));
        return  modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public AccountDTO getAccountByNickname(String nickname) {
        Account account = getAccount(nickname);
        return modelMapper.map(account,AccountDTO.class);
    }

    @Override
    public String saveUser(SignUpDTO signUpDTO) {
        signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        Account account = modelMapper.map(signUpDTO, Account.class);
        account.beginningRole();
        account.generateEmailToken();
        accountRepository.save(account);
        //sendSignUpConfirmEmail(account);
        return signUpDTO.getNickname();
    }

    @Override
    public boolean confirmEmailToken(String email, String token) {
        try {
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("not found email"));
            if(!account.getEmailCheckToken().equals(token)){
                throw new IllegalArgumentException("");
            }
            account.EmailConfirmRole();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProfile(ProfileDTO profileDTO, String nickname) {
        Account account = getAccount(nickname);
        account.updateProfile(profileDTO);
        return true;
    }

    @Override
    public ProfileDTO getProfileByNickname(String nickname) {
        Account account = getAccount(nickname);
        ProfileDTO profileDTO = new ProfileDTO(account);
        return profileDTO;
    }

    @Override
    public void updatePassword(PasswordDTO passwordDTO, String nickname) {
        Account account = getAccount(nickname);
        if(passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())){
            throw new IllegalArgumentException("password dont match ");
        }
        passwordDTO.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        account.changePassword(passwordDTO);
    }

    @Override
    public void addTag(String nickname, Tag tag) {
        Account account = getAccount(nickname);
        account.getTags().add(tag);
    }

    @Override
    public void deleteTag(String tagTitle, String nickname) {
        Account account = getAccount(nickname);
        Tag tag = tagRepository.findByTitle(tagTitle).get();
        account.getTags().remove(tag);
        tagRepository.delete(tag);
    }

    @Override
    public List<String> getZone(String nickname) {
        Account zonesByNickname = accountRepository.findZonesByNickname(nickname);
        if(zonesByNickname.getZones() != null){
            List<String> collect  = zonesByNickname.getZones().stream()
                    .map(Zone::getLocalNameOfCity).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    @Override
    public void addZone(List<ZoneDTO> zoneData,String nickname) {
        Account account = accountRepository.findZonesByNickname(nickname);
        List<String> collect = zoneData.stream().map(ZoneDTO::getValue).collect(Collectors.toList());
        Set<Zone> zones = zoneRepository.findByLocalNameOfCityIn(collect);
        account.getZones().clear();
        zones.forEach(zone -> account.getZones().add(zone));
    }

    @Override
    public String uploadProfileImage(String nickname, ProfileImageDTO file) {
        Account account = accountRepository.findByNickname(nickname).get();
        String originName = file.getFile().getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        Path savePath = Paths.get(uploadPath, uuid+"_"+originName);
        try {
            file.getFile().transferTo(savePath);
            account.uploadProfileImage(savePath.toString());
        }catch (IOException e){
            log.info(e);
        }
        return savePath.toString();
    }

    private Account getAccount(String nickname) {
        return accountRepository.findByNickname(nickname).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Account userdetailservice.......");
        Optional<Account> accountOptional = accountRepository.findByNickname(username);
        Account account = accountOptional.orElseThrow(()-> new UsernameNotFoundException("not found user"));
        CurrentUserDTO currentUserDTO = new CurrentUserDTO(account.getNickname(),account.getPassword(),
                                 account.getRoles().stream().map(roles ->
                                new SimpleGrantedAuthority("ROLE_"+roles.name())).collect(Collectors.toList()));

        log.info(currentUserDTO);
        return currentUserDTO;

    }

    public void sendSignUpConfirmEmail(Account newAccount) {
        Context context = new Context();
        String link = "http://localhost:8888/check-email-token?token="+newAccount.getEmailCheckToken()+"&email="+newAccount.getEmail();
        context.setVariable("link",link);
        context.setVariable("nickname",newAccount.getNickname());
        context.setVariable("linkName","????????? ????????????");
        context.setVariable("message","????????? ???????????? ??????????????? ????????? ???????????????");

        String message = templateEngine.process("mail/confirm-mail",context);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("????????? ?????? ?????? ??????")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }



}
