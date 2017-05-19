package net.moopa.guard.config;

import net.moopa.guard.common.PropertiesFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;

/**
 * Created by LeeAutumn on 11/25/16.
 * blog: leeautumn.net
 */
public class Configs {
    private static HashMap<String,String> configs = new HashMap<String,String>();
    private static Logger logger = LoggerFactory.getLogger(Configs.class);

    static {
        Properties properties = PropertiesFileUtil.getProperties("guard.ini");
        //开始验证 配置 是否正确
        //需要得到用户自己的用户服务实现
        Object o1 = properties.get("guard.GuardService");
        if(o1 == null){
            logger.error("can't get interface IGuardService implement class, please check your config.");
            throw new IllegalStateException();
        }
        configs.put("guard.guardService",o1.toString());

        Object o2 = properties.get("");

    }

    public static String get(String key){
        return configs.get(key);
    }
}
