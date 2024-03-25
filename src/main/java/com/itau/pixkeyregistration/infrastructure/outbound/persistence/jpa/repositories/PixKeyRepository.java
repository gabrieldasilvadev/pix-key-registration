package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories;

import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.PixKeyTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKeyTable, String> {
    @Query(value = "SELECT COUNT(*) FROM tb_pix_key WHERE key_value = :keyValue", nativeQuery = true)
    long countByKeyValue(@Param("keyValue") String keyValue);

    default boolean areKeysUnique(String keyValues) {
        return countByKeyValue(keyValues) == 0;
    }
}
