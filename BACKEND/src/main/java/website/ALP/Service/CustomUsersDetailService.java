package website.ALP.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import website.ALP.Repository.UserRepo;
import website.ALP.model.Users;

@Service
public class CustomUsersDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepo.findByEmail(username).get();
        if (users == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUsersDetail(users);
    }
}
