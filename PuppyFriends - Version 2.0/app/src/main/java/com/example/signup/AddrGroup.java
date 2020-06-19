package com.example.signup;

import java.util.ArrayList;

public class AddrGroup {
    public ArrayList<String> child;

    public String groupName;

    public AddrGroup(String groupName){
        this.groupName = groupName;
        child = new ArrayList<String>();
    }
}
