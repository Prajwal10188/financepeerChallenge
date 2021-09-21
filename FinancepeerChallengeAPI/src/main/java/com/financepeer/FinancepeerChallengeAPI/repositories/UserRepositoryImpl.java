package com.financepeer.FinancepeerChallengeAPI.repositories;

import com.financepeer.FinancepeerChallengeAPI.domain.User;
import com.financepeer.FinancepeerChallengeAPI.domain.sampleData;
import com.financepeer.FinancepeerChallengeAPI.exceptions.fpAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static java.sql.Types.VARCHAR;

public class UserRepositoryImpl implements UserRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_CREATE = "INSERT INTO users(user_id,user_name,user_password) VALUES(NEXTVAL('user_seq'), ?, ?)";
    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM USERS WHERE USER_ID = ?";
    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) FROM USERS WHERE USER_NAME = ?";
    private static final String SQL_INSERT = "INSERT INTO DAT VALUES(?,?,?,?)";
    @Override
    public Integer create(String user_name, String user_password) throws fpAuthException, NullPointerException {
        String hasedUserPassword = BCrypt.hashpw(user_password, BCrypt.gensalt(12));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user_name);
                ps.setString(2, hasedUserPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("hiree_id");
        }catch(Exception e){
            throw new fpAuthException("Invalid details, failed to create account");
        }
    }

    @Override
    public Integer getCountByName(String user_name) throws fpAuthException {
        int[] arr = {VARCHAR};
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_NAME,new Object[]{user_name}, arr,Integer.class);
    }

    @Override
    public User findById(Integer user_id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_USER_ID,  userRowMapper, new Object[]{user_id});
    }

    @Override
    public void insertDataIntoTable(List<sampleData> list) {
        for(int i = 0; i<list.size(); i++){
            int finalI = i;
            jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, list.get(finalI).getUserID());
                    ps.setString(2, list.get(finalI).getID());
                    ps.setString(3, list.get(finalI).getTitle());
                    ps.setString(4, list.get(finalI).getBody());
                    return ps;
                });
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("user_password")
        );
    });
}
