package com.example.repository;

import com.example.model.Defect;
import com.example.model.UserStory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DefectRepository {

    private static List<Defect> defectList = new ArrayList<>();

    public static boolean isEmpty() {
        return defectList.isEmpty();
    }

    public static boolean add(Defect defect) {
        return defectList.add(defect);
    }

    public static boolean contains(Object o) {
        return defectList.contains(o);
    }

    public static boolean remove(Object o) {
        return defectList.remove(o);
    }



    public static void clear() {
        defectList.clear();
    }
}