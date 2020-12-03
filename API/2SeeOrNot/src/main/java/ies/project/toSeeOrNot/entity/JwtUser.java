package ies.project.toSeeOrNot.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Wei
 * @date 02/12/2020 16:51
 */

/**
 * Spring security User Entity
 */
public class JwtUser implements UserDetails {
    private final static String[] ROLES = new String[]{"ROLE_USER", "ROLE_CINEMA", "ROLE_ADMIN"};
    private final Integer ID;
    private final String EMAIL;
    private final String PASSWORD;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(User user) {
        this.ID = user.getId();
        this.EMAIL = user.getUserEmail();
        this.PASSWORD = user.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(ROLES[user.getRole()]));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getUsername() {
        return EMAIL;
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
