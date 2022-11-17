package com.example.studyApi.security.filter;

import com.example.studyApi.util.JWTUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class ApiLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("---- APILOGINFILTER -----");

        if(request.getMethod().equals("GET")){
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }
        Map<String,String > jsonData = parseRequestJSON(request);
        log.info(jsonData);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(jsonData.get("nickname"),jsonData.get("password"));
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();
        log.info("successAuthentication........");
        log.info(authentication.getName());
        log.info(user.getAuthorities());



        List<String> roleList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String,Object> claim = Map.of("nickname",authentication.getName(),
                "roles",roleList);
        String accessToken = jwtUtil.generateToken(claim,1);
        String refreshToken = jwtUtil.generateToken(claim,10);

        Gson gson = new Gson();
        log.info(accessToken);

        Map<String,Object> keyMap = Map.of("accessToken",accessToken ,
                "refreshToken",refreshToken,"nickname",user.getUsername(),
                "roles",roleList);
        String jsonStr = gson.toJson(keyMap);
        response.getWriter().println(jsonStr);

    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        try {
            Reader reader = new InputStreamReader(request.getInputStream());
            Gson gson = new Gson();
            return gson.fromJson(reader,Map.class);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
