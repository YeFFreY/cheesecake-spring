package org.yeffrey.cheesecakespring.utils;

import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class JsonTestUtils {
    public static ResultMatcher hasLinksCount(int size) {
        return jsonPath("$._links.*", hasSize(size));
    }

    public static ResultMatcher hasLink(String link) {
        return jsonPath("$._links", hasKey(link));
    }


}
