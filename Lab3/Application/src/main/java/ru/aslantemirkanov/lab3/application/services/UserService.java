package ru.aslantemirkanov.lab3.application.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aslantemirkanov.lab3.application.exception.user.TryToAddExistUserException;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatOwner;
import ru.aslantemirkanov.lab3.dataaccess.entities.Role;
import ru.aslantemirkanov.lab3.dataaccess.entities.User;
import ru.aslantemirkanov.lab3.dataaccess.repository.CatOwnerRepository;
import ru.aslantemirkanov.lab3.dataaccess.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatOwnerRepository catOwnerRepository;

    @Autowired
    CatOwnerServiceImpl catOwnerService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mappingRoles(user.getRoles()));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    private Collection<? extends GrantedAuthority> mappingRoles(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }

    @Transactional
    public void addUser(User user){
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new TryToAddExistUserException(user.getUsername());
        }
        user.setRoles(new ArrayList<>());
        Role role = Role.ROLE_USER;
        user.setRoles(new ArrayList<>(Collections.singletonList(role)));
        userRepository.save(user);
    }


    @Transactional
    public void addCatOwner(User user, Long catOwnerId){
        CatOwner catOwner = catOwnerService.getCatOwner(catOwnerId);
        user.setCatOwner(catOwner);
        catOwner.setUser(user);
        catOwnerRepository.save(catOwner);
        userRepository.save(user);
    }

}

