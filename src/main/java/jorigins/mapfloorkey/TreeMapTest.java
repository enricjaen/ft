package jorigins.mapfloorkey;

import java.util.Set;
import java.util.TreeMap;

public class TreeMapTest {

    public static void main(String a[]){
        TreeMap<Float, String> tm = new TreeMap<>();
        //add key-value pair to TreeMap
        tm.put(30f, "FIRST INSERTED");
        tm.put(20f, "THIRD INSERTED");
        tm.put(10f, "SECOND INSERTED");
        tm.put(40f, "SECOND INSERTED");
        System.out.println(tm);
        Set<Float> keys = tm.keySet();
        for(Float key: keys){
            System.out.println("Value of "+key+" is: "+tm.get(key));
        }

        System.out.println(tm.floorKey(10.01f));
        System.out.println(tm.floorKey(10.00f));
        System.out.println(tm.floorKey(50.5f));

    }
}