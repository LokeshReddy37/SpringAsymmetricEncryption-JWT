package com.LokeshReddy.SpringSecuityAsymmetricEncryption.role;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.common.BaseEntity;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.User;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    List<User> users;

}
