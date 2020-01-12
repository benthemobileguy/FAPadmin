package com.example.bnotion.admin;

public class Agents {
    public String name;
    public String image;
    public String document;
    public String document_type;
    public String status;
    public String date;
    public String user_id;
    public Agents(){

    }
    public Agents(String name, String image, String document, String status, String date, String document_type, String user_id) {
        this.name = name;
        this.image = image;
        this.document = document;
        this.status = status;
        this.date = date;
        this.document_type = document_type;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
