package com.tianque.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author  linlinan
 * Created by linlinan on 2018/4/23.
 * 实体基类
 */
public class BaseDomain {
    protected String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDate createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDate updateDate;
    protected String createUser;
    protected String updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
