package website.ALP.Service;

import website.ALP.Dto.UsersDto;
import website.ALP.model.Users;


public interface UserService {
    Users save(UsersDto usersDto);
    Users getUsers(UsersDto usersDto);
    UsersDto getUserProfile(String email);
    void updateUserProfile(UsersDto usersDto);

}
