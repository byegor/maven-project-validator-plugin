package com.yegor.validator.common.entity;

import java.util.Date;

public class MavenArtifact {

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final Date lastUpdated;

    public MavenArtifact(String groupId, String artifactId, String version, Date lastUpdated) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.lastUpdated = lastUpdated;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getShortVersion() {
        String[] split = version.split("-");
        return split[0];
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
}
