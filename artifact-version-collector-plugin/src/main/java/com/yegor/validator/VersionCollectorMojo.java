package com.yegor.validator;

import com.yegor.validator.common.entity.MavenArtifact;
import com.yegor.validator.common.entity.ParentPomType;
import com.yegor.validator.common.service.DataService;
import com.yegor.validator.common.service.ServiceFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Mojo(name = "collect-parent-pom-versions", defaultPhase = LifecyclePhase.COMPILE)
public class VersionCollectorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}", required = true)
    public File outputDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DataService dataService = ServiceFactory.getDataService();
        Properties versions = new Properties();
        for (ParentPomType pom : ParentPomType.values()) {
            MavenArtifact artifact = dataService.getArtifact(pom.groupId, pom.groupId);
//            MavenArtifact artifact = new MavenArtifact("gr", "ar", "4.2", new Date());
            versions.put(artifact.getGroupId() + ":" + artifact.getArtifactId(), artifact.getShortVersion());
        }

        storeVersions(versions);
    }

    private void storeVersions(Properties versions) throws MojoExecutionException {
        try {
            Path directory = Paths.get(outputDir.getAbsolutePath(), "classes");
            Files.createDirectories(directory);
            Path path = Files.createFile(directory.resolve("version.properties"));

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                versions.store(writer, null);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to store versions: " + e.getMessage(), e);
        }
    }
}
