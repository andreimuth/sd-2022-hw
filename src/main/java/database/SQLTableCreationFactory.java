package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case USER -> "CREATE TABLE IF NOT EXISTS user (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  email VARCHAR(200) NOT NULL," +
                    "  password VARCHAR(64) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX username_UNIQUE (email ASC));";

            case CLIENT -> "CREATE TABLE IF NOT EXISTS client (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  name varchar(500) NOT NULL," +
                    "  personal_numerical_code varchar(500) NOT NULL," +
                    "  address varchar(500) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX personal_numerical_code_UNIQUE (personal_numerical_code ASC)" +
                    ");";

            case ACCOUNT -> "CREATE TABLE IF NOT EXISTS account (" +
                    "   id int(11) NOT NULL auto_increment," +
                    "   card_number varchar(500) NOT NULL," +
                    "   client_id int(11) NOT NULL," +
                    "   type varchar(500) NOT NULL," +
                    "   amount_of_money int(11) NOT NULL, " +
                    "   date_of_creation datetime DEFAULT NULL," +
                    "   primary key (id), " +
                    "   UNIQUE INDEX card_number_UNIQUE (card_number ASC)," +
                    "   CONSTRAINT client_id" +
                    "    FOREIGN KEY (client_id)" +
                    "    REFERENCES client (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";

            case ROLE -> "  CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case RIGHT -> "  CREATE TABLE IF NOT EXISTS `right` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `right` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";
            case ROLE_RIGHT -> "  CREATE TABLE IF NOT EXISTS role_right (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role_id INT NOT NULL," +
                    "  right_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  INDEX right_id_idx (right_id ASC)," +
                    "  CONSTRAINT role_id" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT right_id" +
                    "    FOREIGN KEY (right_id)" +
                    "    REFERENCES `right` (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";
            case USER_ROLE -> "\tCREATE TABLE IF NOT EXISTS user_role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  role_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX user_id_idx (user_id ASC)," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  CONSTRAINT user_fkid" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT role_fkid" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";

            case EVENT -> " CREATE TABLE IF NOT EXISTS event (" +
                    "   id INT NOT NULL AUTO_INCREMENT," +
                    "   user_id int not null," +
                    "   action varchar(500) not null," +
                    "   date datetime default null," +
                    "   primary key(id)," +
                    "   CONSTRAINT user1_fkid" +
                    "   foreign key (user_id)" +
                    "   references user (id)" +
                    "   ON DELETE CASCADE" +
                    "   ON UPDATE CASCADE);";

            default -> "";
        };
    }

}
