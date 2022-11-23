package ru.ssemenov.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssemenov.entities.CustomsDeclaration;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomsDeclarationRepository extends JpaRepository<CustomsDeclaration, String> {
    Optional<CustomsDeclaration> findById(UUID id);

    Page<CustomsDeclaration> findAllByVatCode(String vatCode, Pageable pageable);

    @Transactional
    void deleteById(UUID id);
}
