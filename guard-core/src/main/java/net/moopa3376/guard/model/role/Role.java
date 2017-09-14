package net.moopa3376.guard.model.role;


import net.moopa3376.guard.model.permission.Permission;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Moopa on 18/04/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class Role {
    private Long id;
    private String name;
    private String description = null;
    private Date gmtCreate;
    private Date gmtModified;
    private Map<String,Permission> allowedActionMap = new ConcurrentHashMap<String, Permission>();

    public Role(){

    }
    public Role(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Map<String, Permission> getAllowedActionMap() {
        return allowedActionMap;
    }

    public void setAllowedActionMap(
        Map<String, Permission> allowedActionMap) {
        this.allowedActionMap = allowedActionMap;
    }
}
