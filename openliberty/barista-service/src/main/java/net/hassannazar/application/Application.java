package net.hassannazar.application;

import liquibase.exception.LiquibaseException;
import net.hassannazar.application.liquibase.LiquibaseUpdater;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.sql.SQLException;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Application {

    @Inject
    private
    LiquibaseUpdater updater;

    @PostConstruct
    void startUp() {
        System.out.println("Starting Barista Service..");
        // Run Liquibase update
        try {
            this.updater.update();
        } catch (final SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
