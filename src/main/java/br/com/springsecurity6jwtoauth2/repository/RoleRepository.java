package br.com.springsecurity6jwtoauth2.repository;

import br.com.springsecurity6jwtoauth2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}