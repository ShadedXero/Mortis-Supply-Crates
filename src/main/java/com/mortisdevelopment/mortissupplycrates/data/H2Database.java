package com.mortisdevelopment.mortissupplycrates.data;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class H2Database extends Database {

    private Connection connection;
    private final File file;

    public H2Database(File file, String username, String password) {
        super(null, 0, null, username, password);
        this.file = file;
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            org.h2.Driver driver = new org.h2.Driver();
            this.connection = driver.connect("jdbc:h2:file:" + file.getAbsolutePath(), getProperties());
            return connection;
        }catch (SQLException exp) {
            exp.printStackTrace();
        }
        return null;
    }
}
