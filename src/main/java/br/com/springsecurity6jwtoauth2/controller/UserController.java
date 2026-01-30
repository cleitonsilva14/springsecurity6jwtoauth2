package br.com.springsecurity6jwtoauth2.controller;


import br.com.springsecurity6jwtoauth2.domain.Role;
import br.com.springsecurity6jwtoauth2.domain.User;
import br.com.springsecurity6jwtoauth2.dto.CreateUserDto;
import br.com.springsecurity6jwtoauth2.repository.RoleRepository;
import br.com.springsecurity6jwtoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @PostMapping("/user")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {

        var role = roleRepository.findByName(Role.Values.BASIC.name());

        var username = userRepository.findByUsername(dto.username());

        if(username.isPresent()) {
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();

        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(role));

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }


}
