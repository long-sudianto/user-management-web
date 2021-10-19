package com.bca.usermanagementweb.service;

import java.util.List;

import com.bca.usermanagementweb.model.Response;
import com.bca.usermanagementweb.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    
    @Value("${api.host}/users")
    private String usersApi;
    
    @Value("${api.host}/users/{id}")
    private String usersApiWithId;

    @Autowired
    private RestTemplate restTemplate;

    private Logger log = LoggerFactory.getLogger(UserService.class);

    public List<User> getUsers() {
        ResponseEntity<Response<List<User>>> response = restTemplate.exchange(usersApi, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Response<List<User>>>(){});
        List<User> users = null;
        if(null != response.getBody()) {
	        log.info("getUsers response: "+response.getBody().toString());
	        users = response.getBody().getPayload();
        }
        return users;
    }
    
    public User getUserById(Long id) {
        ResponseEntity<Response<User>> response = restTemplate.exchange(usersApiWithId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Response<User>>(){}, id);
        log.info("getUserById response: "+response.getBody().toString());
        User user = response.getBody().getPayload();
        return user;
    }
    
    public User saveUser(User user) {
        return restTemplate.postForObject(usersApi, user, User.class);
    }

    public User update(Long id, User user) {
        return restTemplate.exchange(usersApiWithId, HttpMethod.PUT, new HttpEntity<>(user), User.class, id).getBody();
    }

    public void delete(Long id) {
        restTemplate.delete(usersApiWithId, id);
    }
}
