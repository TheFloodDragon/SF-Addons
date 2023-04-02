package cn.fd.sfaddons.utils;

import cn.fd.sfaddons.AddonsPlugin;

import java.util.concurrent.Executors;
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
        switch (type) {
            case 0:
                logger.info(msg);
            case 1:
                //logger.warning(msg);
            case 2:
                //logger.severe(msg);
        }
    }

    public static void runTaskAsynchronously(Runnable runnable) {
        Executors.newFixedThreadPool(10).execute(runnable);
    }

}
