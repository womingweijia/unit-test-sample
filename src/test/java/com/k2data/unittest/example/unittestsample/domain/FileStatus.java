package com.k2data.unittest.example.unittestsample.domain;

import java.util.Date;

/**
 * Created by cx on 2018-10-17.
 */
public class FileStatus {
    private Long accessTime;
    private Integer blockSize;
    private Integer childrenNum;
    private Integer fileId;
    private String group;
    private Integer length;
    private Long modificationTime;
    private String owner;
    private String pathSuffix;
    private String permission;
    private Integer replication;
    private Integer storagePolicy;
    private String type;

    public FileStatus(String owner, String pathSuffix, String type) {
        this.owner = owner;
        this.pathSuffix = pathSuffix;
        this.type = type;
        this.accessTime = new Date().getTime();
        this.blockSize = 0;
        this.childrenNum = 0;
        this.fileId = 36110;
        this.group = "supergroup";
        this.length = 0;
        this.modificationTime = new Date().getTime();
        this.replication = 3;
        this.storagePolicy = 0;
        this.permission = "755";
    }

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPathSuffix() {
        return pathSuffix;
    }

    public void setPathSuffix(String pathSuffix) {
        this.pathSuffix = pathSuffix;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getReplication() {
        return replication;
    }

    public void setReplication(Integer replication) {
        this.replication = replication;
    }

    public Integer getStoragePolicy() {
        return storagePolicy;
    }

    public void setStoragePolicy(Integer storagePolicy) {
        this.storagePolicy = storagePolicy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
