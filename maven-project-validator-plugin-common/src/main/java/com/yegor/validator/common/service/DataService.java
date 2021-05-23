package com.yegor.validator.common.service;

import com.yegor.validator.common.entity.MavenArtifact;

public interface DataService {
    MavenArtifact getArtifact(String groupId, String artifactId);
}
