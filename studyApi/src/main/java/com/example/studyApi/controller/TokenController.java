package com.example.studyApi.controller;

import com.example.studyApi.service.AccountService;
import com.example.studyApi.util.JWTUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final AccountService accountService;
    private final JWTUtil jwtUtil;

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization =request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){
            try {
                log.info("---- refresh token filter ------");
                Map<String,String> tokens = parseRequestJson(request);
                String accessToken = tokens.get("accessToken");
                String refreshToken = tokens.get("refreshToken");

                jwtUtil.validateToken(accessToken);

                Map<String, Object> refreshValue = jwtUtil.validateToken(refreshToken);
                //refreshToken 유효기간 얼마남지 않은 경우
                Integer exp =(Integer) refreshValue.get("exp");
                Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);
                Date currentTime = new Date(System.currentTimeMillis());

                //3일 미만인 경우 다시 생성
                long gapTime = (expTime.getTime() - currentTime.getTime());
                String nickname = (String) refreshValue.get("nickname");
                UserDetails userDetails = accountService.loadUserByUsername(nickname);
                Map<String,Object> claim = Map.of("nickname",userDetails.getUsername(),
                        "roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                accessToken = jwtUtil.generateToken(Map.of("nickname",userDetails.getUsername()),1);
                if(gapTime < (1000 * 60  * 3  ) ){
                    refreshToken = jwtUtil.generateToken(Map.of("nickname",userDetails.getUsername()),15);
                }

                log.info("-- refresh token result ...........");
                log.info("-- refresh token : {}",refreshToken);
                log.info("-- access token : {}",accessToken);


                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
                response.getWriter().println(jsonStr);

            }catch (IOException e){
                log.error("Error login {} ", e.getMessage());
            }
        }
    }
    private Map<String, String> parseRequestJson(HttpServletRequest request) throws IOException {
        Reader reader = new InputStreamReader(request.getInputStream());
        Gson gson = new Gson();
        log.info("--- parseJson ---");
        return gson.fromJson(reader, Map.class);
    }

    @GetMapping("/check-email-token")
    public ResponseEntity<Map<String,Boolean>> checkEmailToken(@RequestParam("email")String email,
                                                               @RequestParam("token")String token){
        boolean result = accountService.confirmEmailToken(email, token);
        return ResponseEntity.ok(Map.of("result",result));
    }

}
