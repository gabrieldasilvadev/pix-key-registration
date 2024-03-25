package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities;

import com.itau.pixkeyregistration.domain.pix.enums.PixKeyStatus;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pix_key")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PixKeyTable {
    @Id
    private String id;

    @Column(name = "key_value")
    private String keyValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "key_type")
    private PixKeyType keyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PixKeyStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountTable account;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "inactivated_at")
    private LocalDateTime inactivatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
