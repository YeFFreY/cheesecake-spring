package org.yeffrey.cheesecakespring.features;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiIndexController {

    @GetMapping
    public List<Map<String, String>> index() {
        return Lists.newArrayList(
            Map.of("rel", "activities", "href", "http://localhost:8080/api/activities"),
            Map.of("rel", "activities::details", "href", "http://localhost:8080/api/activities"),
            Map.of("rel", "activities::create", "href", "http://localhost:8080/api/activities")
        );
    }
}
