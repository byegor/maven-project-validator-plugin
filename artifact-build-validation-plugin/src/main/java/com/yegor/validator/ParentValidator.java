package com.yegor.validator;

import com.yegor.validator.common.entity.ParentPomType;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "parent-pom-validation", defaultPhase = LifecyclePhase.VALIDATE)
public class ParentValidator extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    VersionHelper versionHelper = new VersionHelper();

    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenProject parentProject = project.getParent();
        String groupId = parentProject.getGroupId();
        if (isAgillicParent(groupId)) {
            ParentPomType parentType = getParentType(parentProject.getArtifactId());
            if (parentType != null) {
                validateParentPom(parentProject, parentType);
            } else {
                throw new IllegalArgumentException("Parent pom not known: " + parentProject.getArtifactId());
            }
        }
    }

    private void validateParentPom(MavenProject parentProject, ParentPomType parentType) throws MojoExecutionException {
        getLog().info("Starting parent pom validation for type: " + parentType);
        String versionUsedInCurrentProject = parentProject.getVersion();
        if (versionHelper.isSameReleaseVersion(parentType, versionUsedInCurrentProject)) {
            getLog().info("Using correct version for " + ParentPomType.CORE + " parent pom");
        } else {
            throw new MojoExecutionException("Wrong parent pom, expected to see " + versionHelper.getVersion(ParentPomType.CORE));
        }
    }

    private boolean isAgillicParent(String groupId) {
        return "com.agillic.maven".equals(groupId);
    }

    private ParentPomType getParentType(String artifactId) {
        for (ParentPomType pom : ParentPomType.values()) {
            if (pom.artifactId.equals(artifactId)) {
                return pom;
            }
        }
        return null;
    }
}
