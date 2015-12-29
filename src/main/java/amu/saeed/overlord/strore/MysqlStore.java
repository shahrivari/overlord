package amu.saeed.overlord.strore;

import java.io.IOException;
import java.sql.*;

public class MysqlStore implements Store {
    private Connection connection = null;
    private PreparedStatement putStatement = null;
    private PreparedStatement getStatement = null;
    private PreparedStatement delStatement = null;


    public MysqlStore(String connectionStr, String table, int numConnections) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(connectionStr);
        putStatement = connection.prepareStatement("INSERT INTO `" + table + "` (id,val) VALUES " +
                "(?, ?) ON DUPLICATE KEY UPDATE val=VALUES(val)");
        getStatement = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE id =?");
        delStatement = connection.prepareStatement("DELETE FROM `" + table + "` WHERE id =?");
    }

    @Override
    public synchronized void put(long key, byte[] val) throws IOException {
        try {
            putStatement.setLong(1, key);
            putStatement.setBytes(2, val);
            putStatement.executeUpdate();
        } catch (SQLException exp) {
            throw new IOException(exp);
        }
    }

    @Override
    public byte[] get(long key) throws IOException {
        try {
            getStatement.setLong(1, key);
            ResultSet result = getStatement.executeQuery();
            return null;
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void delete(long key) {

    }
}
