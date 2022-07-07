package kg.peaksoft.bilingualb4.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "auth_infos")
@Getter
@Setter
@NoArgsConstructor
public class AuthInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_info_id_seq")
    @SequenceGenerator(name = "auth_info_id_seq", sequenceName = "auth_info_id_seq", allocationSize = 1)
    Long id;
    private String email;
    private String password;
    @Column(name="is_account_non_expired")
    private boolean isAccountNonExpired = true;
    @Column(name="is_account_non_locked")
    private boolean isAccountNonLocked = true;
    @Column(name=" is_credentials_non_expired")
    private boolean isCredentialsNonExpired = true;
    @Column(name="is_enabled")
    private boolean isEnabled = true;

    @ManyToMany(cascade = {PERSIST,REMOVE},fetch = FetchType.EAGER)
    @JoinTable(name = "authInfo_roles",
            joinColumns = @JoinColumn(name = "authInfo_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.synchronizedCollection(roles);
    }

    @Override
    public String getUsername() {
        return email;
    }


    public void setAuthInfo1(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        role.setAuthInfo1(this);
    }

}
