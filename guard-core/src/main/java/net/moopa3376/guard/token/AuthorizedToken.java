package net.moopa3376.guard.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Moopa
 * @date 16/05/2017
 * blog: moopa.net
 *
 * 这个类主要是以jwt为载体,用来代表用户验证通过
 */
public class AuthorizedToken {
    public String name = null;

    /**
     * token中有两种储存数据的方式,一种是jwt,一种就是Map
     */
    public String jwtToken= null;
    private Map<String,Object> attachment = new ConcurrentHashMap<String, Object>();



    public void put(String key,Object value){
        attachment.put(key,value);
    }

    public void delete(String key){
        attachment.remove(key);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void updateJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


}
