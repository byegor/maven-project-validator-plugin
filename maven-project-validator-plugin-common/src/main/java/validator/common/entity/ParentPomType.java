package validator.common.entity;

public enum ParentPomType {
    CORE("com.agillic.maven", "agillic-core-parent"); //TODO add service


    public final String groupId;
    public final String artifactId;

    ParentPomType(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }
}
