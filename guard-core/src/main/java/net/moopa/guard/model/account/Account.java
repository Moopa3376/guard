package net.moopa.guard.model.account;

import java.util.Date;
import java.util.Map;

/**
 * Created by Moopa on 18/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class Account {
    public int account_id;
    public String loginname;
    public String password;
    String phone;
    public Date created_time;
    public Date update_time;
    public int role_id;
    public int status;


    public Map<String,Object> attachment;

    public Account(){}

    public Account(String account_id,String loginname,String password,String role_id){
        this.account_id = Integer.parseInt(account_id);
        this.loginname = loginname;
        this.password = password;
        this.role_id = Integer.parseInt(role_id);
    }

    public void put(String key,Object value){
        attachment.put(key,value);
    }

    public void delete(String key){
        attachment.remove(key);
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
