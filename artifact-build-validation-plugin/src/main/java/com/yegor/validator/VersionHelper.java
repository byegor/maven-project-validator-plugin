package com.yegor.validator;

import com.yegor.validator.common.entity.ParentPomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class VersionHelper {

    private static final Logger log = LoggerFactory.getLogger(VersionHelper.class);
    private final Properties versions;

    public VersionHelper() {
        this.versions = new Properties();
        try (InputStream resourceAsStream = VersionHelper.class.getClassLoader().getResourceAsStream("version.properties")) {
            versions.load(resourceAsStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read version properties", e);
        }
    }

    public String getVersion(ParentPomType pomType) {
        return versions.getProperty(pomType.groupId + ":" + pomType.artifactId);
    }

    public boolean isSameReleaseVersion(ParentPomType pomType, String versionUsedInCurrentProject) {
        String latestRelease = getVersion(pomType);
        String version = versionUsedInCurrentProject.split("-")[0];
        log.info("Comparing parent pom versions, latest release: {}, used in project: {}", latestRelease, version);
        return version.equals(latestRelease);
    }
}
