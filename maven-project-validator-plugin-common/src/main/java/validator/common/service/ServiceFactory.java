package validator.common.service;

public class ServiceFactory {

    private static CassandraService cassandraService;


    public static DataService getDataService() {
        if (cassandraService == null) {
            cassandraService = new CassandraService();
        }
        return cassandraService;
    }
}
