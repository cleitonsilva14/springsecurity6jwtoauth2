package br.com.springsecurity6jwtoauth2.controller;

import br.com.springsecurity6jwtoauth2.domain.Role;
import br.com.springsecurity6jwtoauth2.domain.Tweet;
import br.com.springsecurity6jwtoauth2.dto.CreateTweetDto;
import br.com.springsecurity6jwtoauth2.dto.FeedDto;
import br.com.springsecurity6jwtoauth2.dto.FeedItemDto;
import br.com.springsecurity6jwtoauth2.repository.TweetRepository;
import br.com.springsecurity6jwtoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @PostMapping("/tweet")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto,
                                            JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        tweetRepository.save(tweet);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/tweet/{tweetId}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("tweetId") Long tweetId, JwtAuthenticationToken token) {

        var  user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get()
                .getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(Role.Values.ADMIN.name()));

        if( isAdmin || tweet.getUser().getUserUUID().equals(UUID.fromString(token.getName()))){
            tweetRepository.deleteById(tweetId);
        }else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        return ResponseEntity
                .ok()
                .build();
    }


    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size){

        var tweets = tweetRepository
                .findAll(PageRequest.of(page, size, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet
                        -> new FeedItemDto(tweet.getTweetId(), tweet.getContent(), tweet.getUser().getUsername()));

        return ResponseEntity.ok(new FeedDto(tweets.getContent(), page, size, tweets.getTotalPages(), tweets.getTotalElements()));

    }


}
