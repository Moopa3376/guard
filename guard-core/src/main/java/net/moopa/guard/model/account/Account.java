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
    String phone_number;
    String email;
    public Date created_time;
    public Date update_time;
    public Date last_login_time;
    public int status;


    public Map<String,Object> attachment;

    public Account(){}

    public Account(String account_id,String loginname,String password){
        this.account_id = Integer.parseInt(account_id);
        this.loginname = loginname;
        this.password = password;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }
}
