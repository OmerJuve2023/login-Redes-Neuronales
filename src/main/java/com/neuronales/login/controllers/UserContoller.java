package com.neuronales.login.controllers;

import com.neuronales.login.dto.LoginUser;
import com.neuronales.login.dto.NewUser;
import com.neuronales.login.jwt.JwtProvider;
import com.neuronales.login.models.Rol;
import com.neuronales.login.models.RolList;
import com.neuronales.login.models.User;
import com.neuronales.login.services.RolService;
import com.neuronales.login.services.UserService;
import com.neuronales.login.utls.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserContoller {

    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RolService roleService;
    private JwtProvider jwtProvider;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;

    @Autowired
    public UserContoller(PasswordEncoder passwordEncoder, UserService userService, RolService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletResponse httpServletResponse,
                                        @Valid @RequestBody LoginUser loginUser, BindingResult bidBindingResult) {
        if (bidBindingResult.hasErrors())
            return new ResponseEntity<>("Revise sus credenciales", HttpStatus.BAD_REQUEST);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            CookieUtil.create(httpServletResponse, cookieName, jwt, true, -1, "dev-store-demo.firebaseapp.com");
            return new ResponseEntity<>("Sesión iniciada", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Revise sus credenciales", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> resgister(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Revise los campos e intente nuevamente", HttpStatus.BAD_REQUEST);
        User user = new User(newUser.getUserName(), passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if (newUser.getRoles().contains("admin24154545154545aADASKskjdka****/"))
            roles.add(roleService.getByRolName(RolList.ROLE_ADMIN).get());
        roles.add(roleService.getByRolName(RolList.ROLE_USER).get());
        user.setRoles((List<Rol>) roles);
        userService.save(user);
        return new ResponseEntity<>("Registro exitoso! Inicie sesión", HttpStatus.CREATED);
    }
}
