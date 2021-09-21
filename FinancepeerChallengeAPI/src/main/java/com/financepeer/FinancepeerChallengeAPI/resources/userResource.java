package com.financepeer.FinancepeerChallengeAPI.resources;
import com.financepeer.FinancepeerChallengeAPI.Constants;
import com.financepeer.FinancepeerChallengeAPI.domain.User;
import com.financepeer.FinancepeerChallengeAPI.domain.sampleData;
import com.financepeer.FinancepeerChallengeAPI.services.UserServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("api/users")
public class userResource {
    @Autowired
    UserServices userServices;
    @PostMapping("/registerUser")
    public ResponseEntity<Map<String , String>> registerUser(@RequestBody Map<String, Object> userMap){
        String user_name = (String) userMap.get("user_name");
        String user_password = (String) userMap.get("user_password");
        User user = userServices.registerUser(user_name, user_password);
        return new ResponseEntity<>(generateJWTTokenUser(user), HttpStatus.OK);
    }
    @PostMapping("/insertData")
    public ResponseEntity<Map<String,Boolean>> insertData(@RequestBody List<sampleData> list){
        userServices.insert(list);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    private Map<String, String> generateJWTTokenUser(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("user_id" , user.getUser_id())
                .claim("user_name" , user.getUser_name())
                .claim("user_password" , user.getUser_password())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
