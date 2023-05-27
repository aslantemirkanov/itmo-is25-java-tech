package ru.aslantemirkanov.lab5.userservice.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aslantemirkanov.lab5.dataservice.entities.CatOwner;
import ru.aslantemirkanov.lab5.dataservice.entities.Role;
import ru.aslantemirkanov.lab5.dataservice.entities.User;
import ru.aslantemirkanov.lab5.dataservice.exceptions.owner.NoneExistCatOwnerException;
import ru.aslantemirkanov.lab5.dataservice.exceptions.user.TryToAddExistUserException;
import ru.aslantemirkanov.lab5.dataservice.repositories.CatOwnerRepository;
import ru.aslantemirkanov.lab5.dataservice.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@ComponentScan("ru.aslantemirkanov.lab5.dataservice.repositories")
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatOwnerRepository catOwnerRepository;

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
        CatOwner catOwner = catOwnerRepository.findById(catOwnerId).orElseThrow(()
                -> new NoneExistCatOwnerException(catOwnerId));
        user.setCatOwner(catOwner);
        catOwner.setUser(user);
        catOwnerRepository.save(catOwner);
        userRepository.save(user);
    }

}

