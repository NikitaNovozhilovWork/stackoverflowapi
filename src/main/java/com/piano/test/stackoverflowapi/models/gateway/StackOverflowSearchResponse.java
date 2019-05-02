package com.piano.test.stackoverflowapi.models.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackOverflowSearchResponse {

    private List<Item> items;
    @JsonProperty("has_more")
    private boolean hasMore;
    @JsonProperty("quota_max")
    private int quotaMax;
    @JsonProperty("quota_remaining")
    private int quotaRemaining;

    @Data
    @Accessors(chain = true)
    public static class Item {
        private List<String> tags;
        private Owner owner;
        @JsonProperty("is_answered")
        private boolean isAnswered;
        @JsonProperty("view_count")
        private int viewCount;
        @JsonProperty("answer_count")
        private int answerCount;
        private int score;
        @JsonProperty("last_activity_date")
        private long lastActivityDate;
        @JsonProperty("creation_date")
        private long creationDate;
        @JsonProperty("last_edit_date")
        private long lastEditDate;
        @JsonProperty("question_id")
        private long questionId;
        private String link;
        private String title;
    }

    @Data
    @Accessors(chain = true)
    public static class Owner {
        private int reputation;
        @JsonProperty("user_id")
        private int userId;
        @JsonProperty("user_type")
        private String userType;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("display_name")
        private String displayName;
        private String link;
    }

}
