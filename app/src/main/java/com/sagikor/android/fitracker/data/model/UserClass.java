package com.sagikor.android.fitracker.data.model;

public  class UserClass{
    private String className;
    private String key;

    public UserClass() {}

    public UserClass(String className) {
        this.className = className;
    }

    public UserClass(String className,String key) {
        this.className = className;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
