package com.example.signup;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

// 견주들이 작성한 정보를 저장할 클래스
public class SittingDetailInfo {
    public String id;
    public String type;

    public SittingDetailInfo(){ }

    public SittingDetailInfo(String id){
        this.id = id;
        this.type = "not-sitter";
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("type", type);

        return result;
    }
}
