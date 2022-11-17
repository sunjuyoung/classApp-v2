package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Roles;
import com.example.studyApi.dto.*;
import com.example.studyApi.mail.EmailMessage;
import com.example.studyApi.mail.EmailService;
import com.example.studyApi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
        sendSignUpConfirmEmail(account);
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
        context.setVariable("linkName","이메일 인증하기");
        context.setVariable("message","스터디 서비스를 사용하려면 링크를 클릭하세요");

        String message = templateEngine.process("mail/confirm-mail",context);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("스터디 회원 가입 인증")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }
}
