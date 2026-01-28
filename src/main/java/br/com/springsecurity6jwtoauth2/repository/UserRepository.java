package br.com.springsecurity6jwtoauth2.repository;

import br.com.springsecurity6jwtoauth2.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, UUID> {
}