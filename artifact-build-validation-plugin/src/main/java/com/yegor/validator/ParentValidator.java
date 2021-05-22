package com.yegor.validator;

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




    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenProject parent = project.getParent();
        String groupId = parent.getGroupId();
        if(isAgillicParent(groupId)){
            getLog().info("Starting parent pom validation");
            String artifactId = parent.getArtifactId();
            if(isCoreParent(artifactId)){
                String version = parent.getVersion();
            }

        }
    }

    public boolean isAgillicParent(String groupId) {
        return "com.agillic.maven".equals(groupId);
    }

    public boolean isCoreParent(String artifactId) {
        return "agillic-core-parent".equals(artifactId);
    }
}
