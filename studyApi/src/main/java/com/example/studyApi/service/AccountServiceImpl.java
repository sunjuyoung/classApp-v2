package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.dto.AccountDTO;
import com.example.studyApi.dto.CurrentUserDTO;
import com.example.studyApi.mail.EmailMessage;
import com.example.studyApi.mail.EmailService;
import com.example.studyApi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    private final EmailService emailService;



    @Override
    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));
        String mailContent = "<p><b> 안녕하세요." + account.getNickname() +"님, </b></p>";
        mailContent+= "<p>이메일 인증 메일입니다.</p>";
        mailContent+= "<p><a href='/check-email-token?token="+account.getEmailCheckToken()+"&email="+account.getEmail()+"'>인증</a></p>";
        EmailMessage emailMessage = EmailMessage.builder()
                .to(account.getEmail())
                .subject("스터디 회원 가입 인증")
                .message(mailContent)
                .nickname(account.getNickname())
                .build();

        emailService.sendEmail(emailMessage);
        return  modelMapper.map(account, AccountDTO.class);
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


}
