package com.piano.test.stackoverflowapi.models.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SearchResponse {

    private List<Question> questions;
    private boolean hasMore;

    @Data
    @Accessors(chain = true)
    public static class Question {
        private String ownerName;
        private String title;
        private String link;
        private long id;
        private long creationDate;
        private boolean isAnswered;
    }

}
