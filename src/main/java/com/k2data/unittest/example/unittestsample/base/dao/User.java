package com.k2data.unittest.example.unittestsample.base.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;

    private String name;

    private Boolean sex;

    private String section;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fromdate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatetime;

    private Byte status;

    private String img;

    private String resume;

    public User() {
    }

    public User(String name, boolean sex, String section, String birthday, String fromDate) throws ParseException {
        this.name = name;
        this.sex = sex;
        this.section = section;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.birthday = sdf.parse(birthday);
        this.fromdate = sdf.parse(fromDate);
        this.updatetime = new Date();
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setImg(String img) {
        this.img = img;
        return this;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume == null ? null : resume.trim();
    }
}