package br.com.springsecurity6jwtoauth2.repository;

import br.com.springsecurity6jwtoauth2.domain.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}