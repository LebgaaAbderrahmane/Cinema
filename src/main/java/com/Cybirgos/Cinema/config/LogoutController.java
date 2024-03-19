package com.Cybirgos.Cinema.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/logout")  //done
public class LogoutController {
    @Autowired
    LogoutService logoutService;
    public ResponseEntity<String > logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
       logoutService.logout(request,response,authentication);
        return new ResponseEntity<>("success !", HttpStatus.OK);
    }
}
