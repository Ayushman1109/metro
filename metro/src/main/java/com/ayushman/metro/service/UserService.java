package com.ayushman.metro.service;

import com.ayushman.metro.dto.request.UserRequest;
import com.ayushman.metro.dto.response.UserResponse;
import com.ayushman.metro.entity.User;
import com.ayushman.metro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .isAdmin(userRequest.getIsAdmin())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
        user = userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .isAdmin(user.getIsAdmin())
                .name(user.getName())
                .email(user.getEmail())
                .tickets(user.getTickets())
                .build();
    }

    public UserResponse getUserDetailsById(Long userId){
        return userRepository.findById(userId)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .isAdmin(user.getIsAdmin())
                        .name(user.getName())
                        .email(user.getEmail())
                        .tickets(user.getTickets())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
