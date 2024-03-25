package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories;

import com.itau.pixkeyregistration.domain.valueobjects.AccountUpdateTransient;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountTable, String> {

    @Transactional
    @Modifying
    @Query(value = """
             UPDATE tb_account ta 
             INNER JOIN tb_person tp ON ta.person_id = tp.id
             SET(
                     ta.type = COALESCE(:#{#accountUpdateTransient.accountType}),
                     ta.agency_number = COALESCE(:#{#accountUpdateTransient.agencyNumber}),
                     ta.number = COALESCE(:#{#accountUpdateTransient.accountNumber}),
                     tp.first_name = COALESCE(:#{#accountUpdateTransient.firstName}),
                     tp.last_name = COALESCE(:#{#accountUpdateTransient.lastName})
                 )
             WHERE ta.accountNumber = :#{#accountUpdateTransient.accountNumber} 
            """, nativeQuery = true)
    void update(AccountUpdateTransient accountUpdateTransient);

    Optional<AccountTable> findByNumber(String number);

    List<AccountTable> findAllByPersonalDocument(String personalDocument);

    @Query("SELECT max(a.number) FROM AccountTable a")
    String findLastAccountNumber();
}
