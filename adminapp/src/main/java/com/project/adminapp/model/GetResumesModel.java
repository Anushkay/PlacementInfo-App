package com.project.adminapp.model;



public class GetResumesModel {
    public String name ;
    public String url;
    public String user_name;
    public String enrollment;
    public GetResumesModel()
    {

    }

    public GetResumesModel(String name, String url, String user_name,String enrollment) {
        this.name = name;
        this.url = url;
        this.user_name = user_name;
        this.enrollment = enrollment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }
}

