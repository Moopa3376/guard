package net.moopa3376.guard.model.permission;

import java.util.Date;

/**
 * Created by Moopa on 18/04/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class Permission {
    private Long id;
    private String description;

    /**
     * 用来标识这个权限名 - GuardService.
     */
    private String name;
    private Date gmtCreate;
    private Date gmtModified;

    public Permission(){}

    public Permission(Long pid,String name,String desc){
        this.id = pid;
        this.name = name;
        this.description = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
