package net.moopa.guard.token;

import net.moopa.guard.Guard;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Moopa on 16/05/2017.
 * blog: leeautumn.net
 *
 * 这个类主要是以jwt为载体,用来代表用户验证通过
 * @autuor : Moopa
 */
public class AuthorizeToken {
    public String loginname = null;

    //token中有两种储存数据的方式,一种是jwt,一种就是Map
    public String jwtToken= null;
    private Map<String,Object> attachment = new ConcurrentHashMap<String, Object>();



    public void put(String key,Object value){
        attachment.put(key,value);
    }

    public void delete(String key){
        attachment.remove(key);
    }


    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
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
