package com.w3code.library.json;

import java.util.ArrayList;

public class BaseGroup {
    String mBaseGroup;
    ArrayList<Base> mBaseArray;
    public BaseGroup(String baseGroup, ArrayList<Base> baseArray) {
        this.mBaseGroup = baseGroup;
        this.mBaseArray = baseArray;
    }
    public ArrayList<Base> getBaseArray() {
        return mBaseArray;
    }
}