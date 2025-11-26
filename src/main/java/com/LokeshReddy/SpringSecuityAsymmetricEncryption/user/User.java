package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="USERS")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User implements UserDetails {//we need to implement certain details for authentication
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME",nullable = false)
    private String lastName;

    @Column(name = "EMAIL",nullable = false,unique = true)
    private String email;

    @Column(name = "PHONE_NUMBER",nullable = false,unique = true)
    private String phoneNumber;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Column(name="DATE_OF_BIRTH")
    private LocalDate dateOFBirth;

    @Column(name = "IS_ENABLED")
    private boolean enabled;

    @Column(name = "IS_ACCOUNT_LOCKED")
    private boolean locked;

    @Column(name = "IS_CREDENTIALS_EXPIRED")
    private boolean expired;

    @Column(name = "IS_EMAIL_VERIFIED")
    private boolean isEmailVerified;

    @Column(name = "IS_PHONE_VERIFIED")
    private boolean isPhoneNumberVerified;

    @CreatedDate
    @Column(name = "CREATED_AT",updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT",insertable = false)
    private LocalDateTime lastModifiedAt;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},//if the users doesnt have a role then create one else merge the role
        fetch= FetchType.EAGER)//when user is loaded ,it loads the roles with it
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USERS_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLES_ID")
            }
    )
    List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(CollectionUtils.isEmpty(this.roles)){
            return List.of();
        }
        return this.roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }
}
