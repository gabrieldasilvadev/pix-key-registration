package com.itau.pixkeyregistration;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_teste")
public record Teste(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        String name
) {
}
