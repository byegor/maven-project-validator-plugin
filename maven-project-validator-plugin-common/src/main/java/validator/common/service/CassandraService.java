package validator.common.service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import validator.common.entity.MavenArtifact;

class CassandraService implements DataService {

    final Cluster cluster;

    public CassandraService() {
        this.cluster = createCluster();
    }

    @Override
    public MavenArtifact getArtifact(String groupId, String artifactId) {
        Session session = cluster.connect("project_data");
        try {
            ResultSet execute = session.execute("select * from artifacts where groupId = ? and artifactId =?", groupId, artifactId);
            Row one = execute.one();
            if (one != null) {
                return new MavenArtifact(one.getString("groupId"), one.getString("artifactId"), one.getString("version"), one.getTimestamp("updatedAt"));
            } else {
                throw  new IllegalArgumentException("Failed to find artifact by group " + groupId + " and artifact " + artifactId);
            }
        } finally {
            session.close();
        }
    }

    private Cluster createCluster() {
        String host = System.getProperty("cassandra.host");
        String port = System.getProperty("cassandra.port", "9042");
        if (host == null || host.isEmpty()) {
            throw new IllegalArgumentException("Cassandra host not defined");
        }

        return Cluster.builder().addContactPoint(host).withPort(Integer.parseInt(port)).build();
    }
}
