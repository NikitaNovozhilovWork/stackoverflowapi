package com.piano.test.stackoverflowapi.services;

import com.piano.test.stackoverflowapi.gateways.StackOverflowGateway;
import com.piano.test.stackoverflowapi.models.api.SearchRequest;
import com.piano.test.stackoverflowapi.models.api.SearchResponse;
import com.piano.test.stackoverflowapi.models.gateway.StackOverflowSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StackOverflowService {

    private final StackOverflowGateway stackOverflowGateway;

    public SearchResponse search(SearchRequest request) {
        StackOverflowSearchResponse gatewayResponse = stackOverflowGateway.search(
                request.getPage(),
                request.getSize(),
                request.getTitle(),
                request.getOrder().toString(),
                request.getSort().toString(),
                request.getSite());

        return new SearchResponse()
                .setHasMore(gatewayResponse.isHasMore())
                .setQuestions(gatewayResponse.getItems()
                        .stream()
                        .map(item -> new SearchResponse.Question()
                                .setAnswered(item.isAnswered())
                                .setCreationDate(item.getCreationDate())
                                .setLink(item.getLink())
                                .setId(item.getQuestionId())
                                .setOwnerName(item.getOwner().getDisplayName())
                                .setTitle(item.getTitle()))
                        .collect(Collectors.toList()));
    }

}
