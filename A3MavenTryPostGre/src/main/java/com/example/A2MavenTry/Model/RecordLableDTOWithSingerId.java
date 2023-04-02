package com.example.A2MavenTry.Model;

import java.util.ArrayList;
import java.util.List;

public class RecordLableDTOWithSingerId {
    RecordLable recordLable;
    List<Integer> singersId;

    public RecordLableDTOWithSingerId(){}

    public RecordLable getRecordLable() {
        return recordLable;
    }

    public void setRecordLable(RecordLable recordLable) {
        this.recordLable = recordLable;
    }

    public List<Integer> getSingersId() {
        return singersId;
    }

    public void setSingersId(List<Integer> singersId) {
        this.singersId = singersId;
    }
}
