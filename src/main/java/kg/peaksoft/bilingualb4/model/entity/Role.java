package kg.peaksoft.bilingualb4.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToMany(cascade = {MERGE, DETACH, PERSIST, REFRESH}, mappedBy = "roles", fetch = FetchType.EAGER)
    private List<AuthInfo> authInfo;

    public void setAuthInfo1(AuthInfo authInfos) {
        if (authInfo == null) {
            authInfo = new ArrayList<>();
        }
        authInfo.add(authInfos);
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
