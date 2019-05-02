package com.piano.test.stackoverflowapi.controllers;

import com.piano.test.stackoverflowapi.models.api.SearchRequest;
import com.piano.test.stackoverflowapi.models.api.SearchResponse;
import com.piano.test.stackoverflowapi.services.StackOverflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestApiController {

    private final StackOverflowService stackOverflowService;

    @PostMapping("/search")
    public SearchResponse search(@Validated @RequestBody SearchRequest request) {
        return stackOverflowService.search(request);
    }

}
