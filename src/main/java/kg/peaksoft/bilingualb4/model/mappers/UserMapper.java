package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.TestResponse;
import kg.peaksoft.bilingualb4.api.payload.UserRequest;
import kg.peaksoft.bilingualb4.api.payload.UserResponse;
import kg.peaksoft.bilingualb4.model.entity.AuthInfo;
import kg.peaksoft.bilingualb4.model.entity.Test;
import kg.peaksoft.bilingualb4.model.entity.User;
import kg.peaksoft.bilingualb4.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User mapToEntity(Long id, UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        AuthInfo authInfo = new AuthInfo();
        authInfo.setEmail(userRequest.getEmail());
        authInfo.setPassword(user.getPassword());
        authInfo.setAuthInfo1(roleRepository.getByName("CLIENT"));
        user.setAuthInfo(authInfo);
        return user;
    }
    public List<UserResponse> mapToResponse(List<User> userList){
        List<UserResponse> responses = new ArrayList<>();
        for (User user: userList) {
            responses.add(mapToResponse(user));
        }
        return responses;
    }
    public UserResponse mapToResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        if (user.getId() != null) {
            userResponse.setId(String.valueOf(user.getId()));
        }
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        return userResponse;

    }
}
