package cn.fd.sfaddons.data;

import cn.fd.sfaddons.points.PlayerData;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class SQL {

    public static String tableName = "sfaddons";
    public static String tableNonPlayerName = "sfaddonsnon";
    public static String tableRecordName = "sfaddonsrecord";
    public static String tableUUIDName = "sfaddonsuuid";
    public static String tableLoginName = "sfaddonslogin";
    public final static DatabaseConnection database = new DatabaseConnection();
    static final String encoding = "utf8";

    public static boolean hasnonplayerplugin = false;


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

            String query = "create table if not exists " + tableName +
                    "(UID varchar(50) not null, player varchar(50) not null, rpoints double(20,2) not null, " +
                    "primary key (UID));";

            statement.executeUpdate(query);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getPlayerData(UUID uuid) {
        try {
            Connection connection = database.getConnectionAndCheck();
            String sql = "select * from " + tableName + " where UID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid.toString());

            ResultSet rs = statement.executeQuery();
//            if (rs.next()) {
//                UUID fuuid = uuid;
//
//                BigDecimal cacheThisAmt = DataFormat.formatString(rs.getString(3));
//                PlayerData bd = new PlayerData(fuuid, rs.getString(2), cacheThisAmt);
//                Cache.insertIntoCache(fuuid, bd);
//            }

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
            String query = "select * from " + tableName + " where player = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                UUID uuid = UUID.fromString(rs.getString(1));
//                UUID puuid = null;
//
//                String username = rs.getString(2);
//                BigDecimal cacheThisAmt = DataFormat.formatString(rs.getString(3));
//                if (cacheThisAmt != null) {
//                    PlayerData bd = new PlayerData(uuid, username, cacheThisAmt);
//                    Cache.insertIntoCache(uuid, bd);
//                }
//                break;
//            }

            rs.close();
            statement.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

    }


    public static void save(PlayerData pd) {
        Connection connection = database.getConnectionAndCheck();
        try {
            String query = " set rpoints = ? where UID = ?";

            PreparedStatement statement = connection.prepareStatement("update " + tableName + query);
            statement.setDouble(1, pd.getPoints().doubleValue());

            statement.setString(2, pd.getUniqueId().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveAll(String targetType, List<UUID> players, BigDecimal amount) {
        Connection connection = database.getConnectionAndCheck();
        try {
            if (targetType.equalsIgnoreCase("all")) {
                String query;
                if (amount.compareTo(new BigDecimal(0)) == 0) {
                    query = " set rpoints = rpoints + " + amount.doubleValue();
                } else {
                    query = " set rpoints = rpoints - " + amount.doubleValue();
                }
                PreparedStatement statement = connection.prepareStatement("update " + tableName + query);
                statement.executeUpdate();
                statement.close();
            } else if (targetType.equalsIgnoreCase("online")) {
                StringBuilder query;
                if (amount.compareTo(new BigDecimal(0)) == 0) {
                    query = new StringBuilder(" set rpoints = rpoints + " + amount + " where");
                } else {
                    query = new StringBuilder(" set rpoints = rpoints - " + amount + " where");
                }
                int jsm = players.size();
                int js = 1;

                for (UUID u : players) {
                    if (js == jsm) {
                        query.append(" UID = '").append(u.toString()).append("'");
                    } else {
                        query.append(" UID = '").append(u.toString()).append("' OR");
                        js = js + 1;
                    }
                }
                PreparedStatement statement = connection.prepareStatement("update " + tableName + query);
                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deletePlayerData(String UUID) {
        Connection connection = database.getConnectionAndCheck();
        try {
            String query = "delete from " + tableName + " where UID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, UUID);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}