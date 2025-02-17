package com.alten.ecommerce.service;

import com.alten.ecommerce.mapper.UserMapper;
import com.alten.ecommerce.model.UserDto;
import com.alten.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    @NonNull private final UserRepository userRepository;
    @NonNull private final UserMapper userMapper;

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public void createUser(UserDto userDto) {
        requireNonNull(userDto);
        log.info(" create new user ");
        var user = userRepository.save(userMapper.toEntity(userDto));
        userMapper.toDto(user);
    }
}
