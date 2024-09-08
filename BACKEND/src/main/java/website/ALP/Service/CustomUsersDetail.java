package website.ALP.Service;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import website.ALP.model.Users;

public class CustomUsersDetail implements UserDetails {

    private Users users;

    public CustomUsersDetail(Users users) {
        this.users = users;
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
