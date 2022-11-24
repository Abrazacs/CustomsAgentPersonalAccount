package ru.ssemenov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssemenov.entities.Role;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
