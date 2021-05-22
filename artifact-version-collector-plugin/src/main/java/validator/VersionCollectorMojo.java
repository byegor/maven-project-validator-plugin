package validator;

import validator.common.entity.MavenArtifact;
import validator.common.entity.ParentPomType;
import validator.common.service.DataService;
import validator.common.service.ServiceFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

@Mojo(name = "collect-parent-pom-versions", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class VersionCollectorMojo extends AbstractMojo {

    @Parameter(property = "build.outputDirectory")
    public String outputDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DataService dataService = ServiceFactory.getDataService();
        Properties versions = new Properties();
        for (ParentPomType pom : ParentPomType.values()) {
            MavenArtifact artifact = dataService.getArtifact(pom.groupId, pom.groupId);
            versions.put(artifact.getGroupId() + ":" + artifact.getArtifactId(), artifact.getShortVersion());
        }

        storeVersions(versions);
    }

    private void storeVersions(Properties versions) throws MojoExecutionException {
        Path path = Paths.get(outputDir, "version.properties");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            versions.store(writer, "Created at: " + new Date());
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to store versions", e);
        }
    }
}
