package br.com.springsecurity6jwtoauth2.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItens, int page, int size, int totalPage, Long totalElements) {

}
