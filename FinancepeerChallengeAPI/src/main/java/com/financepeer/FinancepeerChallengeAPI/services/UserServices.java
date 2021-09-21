package com.financepeer.FinancepeerChallengeAPI.services;

import com.financepeer.FinancepeerChallengeAPI.domain.User;
import com.financepeer.FinancepeerChallengeAPI.domain.sampleData;

import java.util.List;

public interface UserServices {
    User registerUser(String user_name, String user_password);
    void insert(List<sampleData> list);
}
