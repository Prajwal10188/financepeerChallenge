package com.financepeer.FinancepeerChallengeAPI.services;

import com.financepeer.FinancepeerChallengeAPI.domain.User;
import com.financepeer.FinancepeerChallengeAPI.domain.sampleData;
import com.financepeer.FinancepeerChallengeAPI.exceptions.fpAuthException;
import com.financepeer.FinancepeerChallengeAPI.repositories.UserRepository;
import com.financepeer.FinancepeerChallengeAPI.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServicesImpl implements UserServices{
    @Autowired
    UserRepository userRepository;
    @Override
    public User registerUser(String user_name, String user_password) {
        Integer count = userRepository.getCountByName(user_name);
        if(count  >0)
            throw new fpAuthException("User name not available, try another user name");
        Integer user_id = userRepository.create(user_name,user_password);
        return userRepository.findById(user_id);
    }

    @Override
    public void insert(List<sampleData> list) {
        userRepository.insertDataIntoTable(list);
    }
}
