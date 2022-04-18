package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoginRepositoryTest {

    @Autowired
    LoginRepository loginRepository;

    @Test
    void findByUserName() {
        Login login = new Login();
        login.setUserName("testUser");
        login.setPassword("testPassword");
        loginRepository.save(login);

        Login testUser = loginRepository.findByUserName("testUser");
        Assertions.assertEquals(login, testUser);
    }
}