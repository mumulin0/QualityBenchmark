package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 7th
 * Process the api(new) output and obtain all the pairs in the following. 976:[48233019, 1306419, 3410871]
 * [[0, 1], [1, 2], [0, 2], [2, 6], [1, 6], [0, 6]]
 */
public class ProcessPair_API {
    // Process the api output and obtain all the pairs in the following. 976:[48233019, 1306419, 3410871]
    public ArrayList<Set> Pairs_API (ArrayList<HashMap<String, ArrayList<Set>>> findnew_results,ArrayList<HashMap<String, ArrayList<Set>>> segment_results){
        ArrayList<Set> results = new ArrayList<Set>();
        Map<String, ArrayList<Set>> m = new HashMap<String, ArrayList<Set>>();
        Map<String, ArrayList<Set>> n = new HashMap<String, ArrayList<Set>>();
        String index = null;
        String ans_index = "";

        for (int i = 0; i < findnew_results.size(); i++) {
            m = segment_results.get(i);  // big
            n = findnew_results.get(i);  // small

            Iterator<Map.Entry<String, ArrayList<Set>>> iter1 = m.entrySet().iterator();
            ArrayList<Set> ans = new ArrayList<Set>();
            ArrayList<Set> ans_notin = new ArrayList<Set>();
            ArrayList<Set> new_concept_list = new ArrayList<Set>();

            while (iter1.hasNext()) {
                Map.Entry<String, ArrayList<Set>> entry1 = (Map.Entry<String, ArrayList<Set>>) iter1.next();
                String numStr = entry1.getKey();
                if (!entry1.getValue().equals(n.get(entry1.getKey()))) { //two maps: same key with different value
                    ArrayList<Set> list_m = entry1.getValue(); // golden_std  [[0, 43567535],...]
                    ArrayList<Set> list_n = n.get(entry1.getKey()); //API  [[43567535],...]
                    for (int p = 0; p < list_n.size(); p++) {
                        for (int q = 0; q < list_m.size(); q++) {
                            boolean contains = list_m.get(q).containsAll(list_n.get(p));
                            if (contains) {
                                ans.add(list_m.get(q));    // 984:[[43567535],[1306419],[243433]]
                                ans_notin.add(list_n.get(p));//984:[[26525204], [4503434], [41176535],
                                                                // [47243238], [38291736], [25699056], [9941859]]
                            }
                        }
                    }

                    //The new added elements.
                    int number = 0;
                    for (int ss = 0; ss < list_n.size(); ss++){
                        if (!ans_notin.contains(list_n.get(ss))){
                            new_concept_list.add(list_n.get(ss));
                            number ++;
                        }
                    }
                    ArrayList<String> index_all = new ArrayList<String>();
                    for (int k = 0; k < ans.size(); k++) {
                        Set ans_set = new LinkedHashSet();
                        ans_set = ans.get(k);   // [0,43567535]
                        Iterator<String> iter = ans_set.iterator();
                        ArrayList<String> content = new ArrayList<String>();
                        ans_index = new String();

                        while (iter.hasNext()) {
                            content.add(iter.next());
                            index = content.get(0); // [0]
                        }
                        ans_index += index;
                        index_all.add(ans_index); //[0,3,8]
                    }
                    DoPairs dopair = new DoPairs();
                    Set<ArrayList> dopair_results = dopair.doPairs(index_all);
                    results.add(dopair_results);
//                    System.out.print(numStr + ":" + new_concept_list + "\r\n");
//                    System.out.println(numStr + ":" + dopair_results + "\r\n");

                    // write the output to new_concept_list.txt/ProcessPair_API.txt results
//                    Properties prop = new Properties();
//                    try{
//                        prop.load(ProcessPair_API.class.getClassLoader().getResourceAsStream("benchmark.properties"));
//                        String ProcessPair_API_fileName = prop.getProperty("ProcessPair_API.filename");
//                        String new_concept_list_fileName = prop.getProperty("new_concept_list.filename");
//
//                        WriteFile.writeFileRepet(ProcessPair_API_fileName, numStr, dopair_results);
//                        WriteFile.writeFileRepet(new_concept_list_fileName, numStr + " -> "+ number + " ", new_concept_list);
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }

                }
            }
        }
        return results;
    }

}
