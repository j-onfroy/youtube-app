package com.company.youtubeclone.service;


import com.company.youtubeclone.dto.UserInfoDTO;
import com.company.youtubeclone.mapper.UserMapper;
import com.company.youtubeclone.model.User;
import com.company.youtubeclone.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndpoint;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public String registerUser(String tokenValue) {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {

            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = responseString.body();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDTO userInfoDTO = objectMapper.readValue(body, UserInfoDTO.class);

            Optional<User> bySub = userRepository.findBySub(userInfoDTO.getSub());
            if (bySub.isPresent()) {
                return bySub.get().getId();
            } else {
                User user = userMapper.userInfoDTOtoUser(userInfoDTO);
                return userRepository.save(user).getId();
            }

        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while registering user", e);
        }

    }
}
