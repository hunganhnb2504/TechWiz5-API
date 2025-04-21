package com.techwiz5.techwiz5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
@Entity
@ToString
public class User extends BaseEntity implements UserDetails {


    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private Date resetTokenExpiry;

    @Column(name = "preferred_currency")
    private String preferredCurrency;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "travel_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "preference")
    private List<String> travelPreferences;

    @Column(name = "profile_picture_url")
    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL should be a valid URL")
    private String profilePictureUrl;

    private Role role;

    private String userType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Long getId() {
        return super.getId();
    }

}
