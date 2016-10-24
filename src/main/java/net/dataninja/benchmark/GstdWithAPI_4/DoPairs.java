package net.dataninja.benchmark.GstdWithAPI_4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lindu on 10/6/2016.
 * Implement the permutations and output all the pairs
 */
public class DoPairs {
    public Set<ArrayList> doPairs(ArrayList<String> orderlist){
        Set<ArrayList> orderpairs = new HashSet<>();
        for(int i = 0; i < orderlist.size(); i++){
            for(int j = i + 1; j < orderlist.size(); j++) {
                ArrayList<String> validcodes = new ArrayList<String>();
                validcodes.add(orderlist.get(i));
                validcodes.add(orderlist.get(j));
                //System.out.println(validcodes);
                orderpairs.add(validcodes);
                //System.out.println(orderpairs);
            }
        }
        return orderpairs;
    }
}
