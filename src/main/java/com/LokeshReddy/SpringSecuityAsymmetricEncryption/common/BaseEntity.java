package com.LokeshReddy.SpringSecuityAsymmetricEncryption.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;

    @Column(name = "CREATED_BY")
    private LocalDateTime createBy;
    private LocalDateTime lastModifiedBy;
}
