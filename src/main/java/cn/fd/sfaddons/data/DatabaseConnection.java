package cn.fd.sfaddons.data;

import cn.fd.sfaddons.AddonsPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static cn.fd.sfaddons.utils.Utils.log;

public class DatabaseConnection {

    private String driver = "org.sqlite.JDBC";

    public static final File dataFolder = new File(AddonsPlugin.getInstance().getDataFolder(), "playerdata");
    //private String url = "";
    //    private final int maxPoolSize = DataBaseConfig.config.getInt("Pool-Settings.maximum-pool-size");
//    private final int minIdle = DataBaseConfig.config.getInt("Pool-Settings.minimum-idle");
//    private final int maxLife = DataBaseConfig.config.getInt("Pool-Settings.maximum-lifetime");
//    private final Long idleTime = DataBaseConfig.config.getLong("Pool-Settings.idle-timeout");
    private boolean secon = false;

    //public int waittimeout = 10;

    public File userdata = new File(dataFolder, "data.db");

    private Connection connection = null;
    private boolean isfirstry = true;

    public boolean setGlobalConnection() {
        try {
            Class.forName(driver);
            if (AddonsPlugin.conf.storage_type.equalsIgnoreCase("SQLite")) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + userdata.toString());
            }

            if (secon) {
                log("SQLite 重新连接成功");
            } else {
                secon = true;
            }
            return true;

        } catch (SQLException e) {
            log("无法连接到数据库-----", 1);
            e.printStackTrace();
            close();
            return false;

        } catch (ClassNotFoundException e) {
            log("JDBC驱动加载失败", 1);
        }

        return false;
    }

    public Connection getConnectionAndCheck() {
        if (!canConnect()) {
            return null;
        }
        try {
            return getConnection();
        } catch (SQLException e1) {
            if (isfirstry) {
                isfirstry = false;
                close();
                return getConnectionAndCheck();
            } else {
                isfirstry = true;
                log("无法连接到数据库-----", 1);
                close();
                e1.printStackTrace();
                return null;
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canConnect() {
        try {
            if (connection == null) {
                return setGlobalConnection();
            }

            if (connection.isClosed()) {
                return setGlobalConnection();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log("SQLite 连接断开失败",1);
            e.printStackTrace();
        }
    }
}
