package com.k2data.unittest.example.unittestsample.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.k2data.unittest.example.unittestsample.base.dao.User;
import com.k2data.unittest.example.unittestsample.base.utils.MyUtil;

import java.util.Date;

/**
 * Created by cx on 2018-10-16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
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

    private Integer age;

    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getSex(), user.getSection(), user.getBirthday(), user.getFromdate(), user.getUpdatetime(), user.getStatus(), user.getImg(), user.getResume(), MyUtil.getAge(user.getBirthday()));
    }

    public UserResponse(Integer id, String name, Boolean sex, String section, Date birthday, Date fromdate, Date updatetime, Byte status, String img, String resume, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.section = section;
        this.birthday = birthday;
        this.fromdate = fromdate;
        this.updatetime = updatetime;
        this.status = status;
        this.img = img;
        this.resume = resume;
        this.age = age;
    }

    public UserResponse setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getSection() {
        return section;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public Byte getStatus() {
        return status;
    }

    public String getImg() {
        return img;
    }

    public String getResume() {
        return resume;
    }

    public Integer getAge() {
        return age;
    }
}
