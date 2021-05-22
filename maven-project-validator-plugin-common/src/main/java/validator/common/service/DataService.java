package validator.common.service;

import validator.common.entity.MavenArtifact;

public interface DataService {
    MavenArtifact getArtifact(String groupId, String artifactId);
}
