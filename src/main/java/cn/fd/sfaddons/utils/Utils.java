package cn.fd.sfaddons.utils;

import cn.fd.sfaddons.AddonsPlugin;

import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static String parseMsg(String msg) {
        if (msg != null)
            return msg.replace('&', '§');
        return null;
    }

    public static void log(String msg) {
        log(msg, 0);
    }

    public static void log(String msg, int type) {
        Logger logger = AddonsPlugin.getInstance().getLogger();
        if (type == 0)
            logger.log(Level.INFO, msg);
        else if (type == 1)
            logger.log(Level.WARNING, msg);
        else if (type == 2)
            logger.log(Level.SEVERE, msg);
    }

    public static void runTaskAsynchronously(Runnable runnable) {
        Executors.newFixedThreadPool(10).execute(runnable);
    }

}
