package database;

import static database.Constants.Schemas.PRODUCTION;
import static database.Constants.Schemas.TEST;

public class DBConnectionFactory {

    public JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if(test) {
            return new JDBConnectionWrapper(TEST);
        }
        return new JDBConnectionWrapper(PRODUCTION);
    }

}
