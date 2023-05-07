package cn.fd.sfaddons.data;

import cn.fd.sfaddons.ReachPoint.PlayerData;

import java.sql.*;
import java.util.UUID;

public class SQL {

    public static String tableName = "sfaddons";

    public final static DatabaseConnection database = new DatabaseConnection();


    public static boolean con() {
        return database.setGlobalConnection();
    }

    public static void close() {
        database.close();
    }

    public static void createTable() {
        try {
            Connection connection = database.getConnectionAndCheck();
            Statement statement = connection.createStatement();

            if (statement == null) {
                return;
            }

            String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(UID varchar(50) NOT NULL, player varchar(50) NOT NULL, repoint double(20,2) NOT NULL, " + "PRIMARY KEY (UID));";

            statement.executeUpdate(query);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getPlayerData(UUID uuid) {
        try {
            Connection connection = database.getConnectionAndCheck();
            String sql = "SELECT * FROM " + tableName + " WHERE UID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid.toString());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                double cacheThisAmt = Double.parseDouble(rs.getString(3));
                PlayerData bd = new PlayerData(uuid, rs.getString(2), cacheThisAmt);
                Cache.insertIntoCache(uuid, bd);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void getPlayerData(String name) {
        try {
            Connection connection = database.getConnectionAndCheck();
            String query = "SELECT * FROM " + tableName + " WHERE player = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString(1));
                String username = rs.getString(2);
                Cache.insertIntoCache(uuid, new PlayerData(uuid, username, Double.parseDouble(rs.getString(3))));
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected static void saveData(PlayerData pd) {
        Connection connection = database.getConnectionAndCheck();
        try {

            String query = " SET repoint = ? WHERE UID = ?";

            PreparedStatement statement = connection.prepareStatement("UPDATE " + tableName + query);
            statement.setDouble(1, pd.getRepoint());
            statement.setString(2, pd.getUniqueId().toString());

            PreparedStatement checker = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE UID = ?");
            checker.setString(1, pd.getUniqueId().toString());
            //检查是否存在该数据
            //如果没有,则创建;如果有,则执行更新命令
            if (checker.executeQuery().next()) statement.execute();
            else createData(pd);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createData(PlayerData pd) {
        Connection connection = database.getConnectionAndCheck();
        try {
            String query = "INSERT INTO " + tableName + "(UID,player,repoint) values(?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, pd.getUniqueId().toString());
            statement.setString(2, pd.getName());
            statement.setDouble(3, pd.getRepoint());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}