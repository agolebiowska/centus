package centus.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.javalite.activejdbc.Base;

public class Db {

    private static final Logger logger = Logger.getLogger(Db.class.getName());
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost/db?verifyServerCertificate=false&useSSL=true";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "pass";

    private static boolean active = false;

    public static void connect() {
        if (!isActive()) {
            Base.open(DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);

            active = true;

            logger.log(Level.INFO, "Connected to database");
        }
    }

    public static void disconnect() {
        Base.close();
        active = false;

        logger.log(Level.INFO, "Disconnected from database");
    }

    public static boolean isActive() {
        return active;
    }
}
