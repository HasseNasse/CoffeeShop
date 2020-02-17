package net.hassannazar.application;

import liquibase.exception.LiquibaseException;
import net.hassannazar.application.liquibase.LiquibaseUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.sql.SQLException;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Inject
    private
    LiquibaseUpdater updater;

    @PostConstruct
    public void startUp() {
        logger.debug("Starting Order Service..");
        // Run Liquibase update
        try {
            this.updater.update();
        } catch (final SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
