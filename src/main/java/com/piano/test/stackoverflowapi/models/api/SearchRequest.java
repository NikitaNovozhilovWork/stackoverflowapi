package com.piano.test.stackoverflowapi.models.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

import static java.lang.String.format;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {

    @Min(value = 1, message = "Page numbering starts with 1")
    private int page;
    @Min(value = 1, message = "Page size must be greater than 0")
    private int size;
    @NotBlank(message = "Words in title for searching is mandatory")
    private String title;
    @NotNull(message = "Ordering cannot be null")
    private Order order;
    @NotNull(message = "Sorting cannot be null")
    private Sort sort;
    @NotBlank(message = "Site for searching is mandatory")
    private String site;

    @Getter
    @AllArgsConstructor
    public enum Order {
        DESC("desc"),
        ASC("asc");

        private String value;

        @Override
        public String toString() {
            return value;
        }

        @JsonCreator
        static Order fromString(String stringValue) {
            return Arrays.stream(Order.values())
                    .filter(order -> order.value.equals(stringValue))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(format("Can't find order for %s", stringValue)));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Sort {
        ACTIVITY("activity"),
        VOTES("votes"),
        CREATION("creation"),
        RELEVANCE("relevance");

        private String value;

        @Override
        public String toString() {
            return value;
        }

        @JsonCreator
        static Sort fromString(String stringValue) {
            return Arrays.stream(Sort.values())
                    .filter(order -> order.value.equals(stringValue))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(format("Can't find sort for %s", stringValue)));
        }
    }

}
