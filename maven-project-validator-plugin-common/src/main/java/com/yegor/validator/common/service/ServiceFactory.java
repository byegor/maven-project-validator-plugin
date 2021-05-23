package com.yegor.validator.common.service;

public class ServiceFactory {

    private static DataService cassandraService;


    // only for tests
    ServiceFactory(DataService dataService) {
        cassandraService = dataService;
    }

    public static DataService getDataService() {
        if (cassandraService == null) {
            cassandraService = new CassandraService();
        }
        return cassandraService;
    }
}
