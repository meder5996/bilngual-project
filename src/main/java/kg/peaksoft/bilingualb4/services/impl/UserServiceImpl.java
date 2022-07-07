package kg.peaksoft.bilingualb4.services.impl;

import kg.peaksoft.bilingualb4.api.payload.UserRequest;
import kg.peaksoft.bilingualb4.api.payload.UserResponse;
import kg.peaksoft.bilingualb4.exception.BadRequestException;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.User;
import kg.peaksoft.bilingualb4.model.mappers.UserMapper;
import kg.peaksoft.bilingualb4.repository.UserRepository;
import kg.peaksoft.bilingualb4.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        log.info("Fetching all clients");
        return userMapper.mapToResponse(userRepository.findAll());
    }

    @Override
    public UserResponse registration(UserRequest userRequest) {
        String email = userRequest.getEmail();
        String[] emailArray = userRequest.getEmail().split("@");
        String[] passwordArray = userRequest.getPassword().split("");
        int counter = 0;
        if (!emailArray[1].equals("gmail.com")) {
            throw new BadRequestException("Email does not correct");
        }
        for (String s : passwordArray) {
            if (Objects.equals(s, s.toUpperCase(Locale.ROOT))) {
                counter++;
            }
        }
        if (passwordArray.length<5){
            throw new BadRequestException("password must have at least 5 characters");
        }
        boolean exists = userRepository.existsByEmail(email);
        log.info("Saving new client {} to the database", userRequest.getUserName());
        if (exists) {
            throw new BadRequestException(
                    String.format("Client with email = %s has already exists", email)
            );
        }
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodedPassword);

        User user = userMapper.mapToEntity(null, userRequest);
        User save = userRepository.save(user);
        return userMapper.mapToResponse(save);
    }

    @Override
    public Optional<User> findById(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new BadRequestException(String.format("while user with %d not in database", id));
        }
        return userRepository.findById(id);
    }

    @Override
    public UserResponse deleteById(Long id) {
        UserResponse userResponse = getById(id);
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new BadRequestException(
                    String.format("User with id = %s does not exists", id)
            );
        }
        userRepository.deleteById(id);
        return userResponse;
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        boolean exists = userRepository.existsById(id);
        User response;
        if (!exists) {
            throw new BadRequestException(
                    String.format("User with %d is already exists", id));
        } else {
            String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
            userRequest.setPassword(encodedPassword);

            response = userMapper.mapToEntity(id, userRequest);
            userRepository.save(response);
        }
        return userMapper.mapToResponse(response);

    }

    private UserResponse getById(Long id) {
        return userMapper.mapToResponse(userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with id = %s does not exists", id)
        )));
    }
}
