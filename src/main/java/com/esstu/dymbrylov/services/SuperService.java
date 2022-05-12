package com.esstu.dymbrylov.services;

import com.esstu.dymbrylov.controllers.ModalController;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperService {
    protected static PreparedStatement preparedStatement;
    protected static Connection connect;
    protected static ResultSet resultSet;

    public Connection ConnectorDB() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:maindb.sqlite");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at ");
            return null;
        }
        return connect;
    }
}
