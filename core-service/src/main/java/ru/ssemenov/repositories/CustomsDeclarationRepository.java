package ru.ssemenov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ssemenov.entities.CustomsDeclaration;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomsDeclarationRepository extends JpaRepository<CustomsDeclaration, UUID>, JpaSpecificationExecutor<CustomsDeclaration> {
    Optional<CustomsDeclaration> findById(UUID id);

    @Transactional
    void deleteById(UUID id);

    @Query(value = "select * from CUSTOMS_DECLARATION where MONTH(DATE_OF_SUBMISSION) >= MONTH(now()) - 1",
            nativeQuery = true)
    List<CustomsDeclaration> getDeclarationOfSubmissionByLastMonth();
}
