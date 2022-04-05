package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.dto.LoginEditDto;
import com.example.nnpia_sem_backend.entity.Login;
import com.example.nnpia_sem_backend.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    public Login findOneLogin(String username){
        return loginRepository.findByUserName(username);
    }

    public Login findByUsername(String username) {
        if (loginRepository.findByUserName(username) != null) {
            return loginRepository.findByUserName(username);
        } else {
            throw new NoSuchElementException("Login with username: " + username + " was not found!");
        }
    }

    public Login findById(Long id) {
        if (loginRepository.findById(id).isPresent()) {
            return loginRepository.findById(id).get();
        } else {
            throw new NoSuchElementException("Login with id: " + id + " was not found.");
        }
    }

    public List<Login> findAll() {
        return loginRepository.findAll();
    }

    public void addLogin(String username, String password){
        Login loginByUsername = findOneLogin(username);

        if(loginByUsername == null){
            Login login = new Login();
            login.setUserName(username);
            login.setPassword(bCryptEncoder.encode(password));
            loginRepository.save(login);
        }
    }

    public boolean updatePassword(LoginEditDto loginDto) {
        Login login = findOneLogin(loginDto.getUsername());

        if (login != null) {
            if (bCryptEncoder.matches(loginDto.getPasswordOld(), login.getPassword())) {
                login.setPassword(bCryptEncoder.encode(loginDto.getPasswordNew()));
                loginRepository.save(login);
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findByUserName(username);
        if (login == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(login.getUserName(), login.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
