package br.com.springsecurity6jwtoauth2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_tweet")
public class Tweet {

    @Id
    @Column(name = "tweet_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tweetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @CreationTimestamp
    private Instant creationTimestamp;

}
