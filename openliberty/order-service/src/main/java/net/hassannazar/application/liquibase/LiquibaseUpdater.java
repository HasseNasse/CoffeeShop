package net.hassannazar.application.liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Purpose:
 * Updates the datasource.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class LiquibaseUpdater {
    private static final Logger logger = LoggerFactory.getLogger(LiquibaseUpdater.class);

    @Resource(lookup = "jdbc/order")
    private DataSource dataSource;

    @Inject
    @ConfigProperty(name = "liquibase.should.run.update")
    private boolean shouldUpdate;

    /**
     * Performs a liquibase update by looking at the db.changelog-master.xml
     * file.
     *
     * @throws SQLException       if connection cannot be established.
     * @throws LiquibaseException if db-impl or update fails.
     */
    public void update() throws SQLException, LiquibaseException {
        if (!this.shouldUpdate) {
            logger.debug("Skipping liquibase update due to property  ::liquibase.should.run.update:: == false");
            return;
        }

        // Get JDBC connection
        final var con = this.dataSource.getConnection();
        final var jdbcCon = new JdbcConnection(con);

        // Initialize Liquibase
        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcCon);
        final Liquibase liquibase = new Liquibase("liquibase/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(),
                database);

        // Perform DB update
        liquibase.update(new Contexts(), new LabelExpression());

    }

}
