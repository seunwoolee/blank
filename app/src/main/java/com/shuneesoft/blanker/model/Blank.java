package com.shuneesoft.blanker.model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public class Blank extends RealmObject{
    private long id;
    private String word;
    @LinkingObjects("blanks")
    private final RealmResults<Article> article = null;

    public Blank() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
