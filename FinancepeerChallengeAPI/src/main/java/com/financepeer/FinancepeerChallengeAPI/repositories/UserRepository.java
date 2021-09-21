package com.financepeer.FinancepeerChallengeAPI.repositories;

import com.financepeer.FinancepeerChallengeAPI.domain.User;
import com.financepeer.FinancepeerChallengeAPI.domain.sampleData;
import com.financepeer.FinancepeerChallengeAPI.exceptions.fpAuthException;

import java.util.List;

public interface UserRepository {
    Integer create(String user_name, String user_password) throws fpAuthException;
    Integer getCountByName(String user_name) throws fpAuthException;
    User findById(Integer user_id) throws fpAuthException;
    void insertDataIntoTable(List<sampleData> list) throws fpAuthException;
}
