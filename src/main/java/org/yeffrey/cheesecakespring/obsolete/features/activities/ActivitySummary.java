package org.yeffrey.cheesecakespring.obsolete.features.activities;

public class ActivitySummary implements Activity {
    private Long id;
    private String name;

    public ActivitySummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
