package com.piano.test.stackoverflowapi.gateways;

import com.piano.test.stackoverflowapi.models.gateway.StackOverflowSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StackOverflowGateway {

    private static final String STACK_OVERFLOW_API = "http://api.stackexchange.com/2.2";
    private static final String SEARCH_TEMPLATE_URL = STACK_OVERFLOW_API + "/search?page={page}&pagesize={pagesize}&order={order}&sort={sort}&intitle={title}&site={site}";

    private final RestTemplate restTemplate;

    public StackOverflowSearchResponse search(int from, int size, String title, String order, String sortBy, String site) {
        Map<String, String> params = new HashMap<>();
        params.put("page", Integer.toString(from));
        params.put("pagesize", Integer.toString(size));
        params.put("order", order);
        params.put("sort", sortBy);
        params.put("title", encodePath(title));
        params.put("site", site);

        return restTemplate.getForObject(
                SEARCH_TEMPLATE_URL,
                StackOverflowSearchResponse.class,
                params);
    }

    @SneakyThrows
    private static String encodePath(String path) {
        return URLEncoder.encode(path, "UTF-8");
    }

}
