package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories;

import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.PersonTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonTable, String> {
    Optional<PersonTable> findByPersonalDocument(String personalDocument);

    boolean existsByPersonalDocument(String personalDocument);
}
