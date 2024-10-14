package com.nhnacademy.webserver.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProjectStatus {
    ACTIVE(1),
    INACTIVE(2),
    TERMINATED(3);

    private final Integer projectStatus;

    ProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public static ProjectStatus fromValue(Integer value) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getProjectStatus().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

    @JsonCreator
    public static ProjectStatus of(String progress) {
        return Arrays.stream(ProjectStatus.values())
                .filter(i -> i.equals(progress))
                .findAny()
                .orElse(null);
    }
}

