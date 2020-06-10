package org.yeffrey.cheesecakespring.features.activities;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {
    @GetMapping
    public List<Map<String, String>> activities() {
        return Lists.newArrayList(
            Map.of("id", "1",
                "name", "Activity title",
                "description", "A fake activity")
        );
    }
}
