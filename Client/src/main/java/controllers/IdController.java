package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Id;

public class IdController {
    private static IdController controller = new IdController();
    Id myId;
    ArrayList<Id> idList = new ArrayList<>();

    private IdController(){

    }

    public Id postId(Id id) {
        return null;
    }

    public static IdController getInstance(){
        return controller;
    }

    public Id putId(Id id) {
        return null;
    }

    public void setId(Id myId){
        this.myId = myId;
    }

    public Id getMyId() {
        return myId;
    }

    public void setMyId(Id myId) {
        this.myId = myId;
    }

    public ArrayList<Id> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<Id> idList) {
        this.idList = idList;
    }

    public Id getId(){
        return myId;
    }
}