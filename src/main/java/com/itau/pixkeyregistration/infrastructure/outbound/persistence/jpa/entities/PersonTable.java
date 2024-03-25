package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities;

import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_person")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PersonTable {
    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "personal_document")
    private String personalDocument;

    @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<AccountTable> accounts;

    @Column(name = "created_at", updatable = false)
    @Setter
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
