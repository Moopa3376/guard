package net.moopa3376.guard.config;

import net.moopa3376.guard.Guard;
import net.moopa3376.guard.common.PropertiesFileUtil;
import net.moopa3376.guard.service.IGuardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by LeeAutumn on 11/25/16.
 * blog: moopa.net
 */
public class GuardConfigs {
    private static HashMap<String,String> configs = new HashMap<String,String>();
    private static Logger logger = LoggerFactory.getLogger(GuardConfigs.class);
    private static Properties properties = null;

    public static boolean init(){
        try {
            properties = PropertiesFileUtil.getProperties("guard-config.properties");
        }catch (Exception e){
            logger.error("get guatd-config.properties, msg: {}",e.getMessage());
            return false;
        }
        return true;
    }

    public static String get(String key){
        if(configs.containsKey(key)){
            return configs.get(key);
        }

        put(properties,key);

        return configs.get(key);

    }

    private static void put(Properties properties,String key){
        Object o1 = properties.get(key);
        if(o1 == null){

        }
        configs.put(key,o1.toString().trim());
    }
}
