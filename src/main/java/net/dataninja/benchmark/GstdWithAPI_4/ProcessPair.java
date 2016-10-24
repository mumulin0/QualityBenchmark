package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 6th
 * Process the gsSet file. Obtain all the pairs in the original following. 994:set(['2'])set(['0', '5'])set(['1', '7'])set(['9', '8', '3', '4', '6'])
 * Attention: number in the same set without order. [0,5],[5,0] are all legal.
 * [[3, 2], [4, 3], [2, 3], [0, 1], [1, 2], [0, 2], [1, 3], [0, 3], [1, 4], [0, 4], [4, 2]]
 */
public class ProcessPair extends HashSet{
    public ArrayList<Set> Pairs(String fileName){
        ArrayList<Set> results = new ArrayList<Set>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                Set<ArrayList> ll = movePair(line);
//                System.out.println(ll);
                results.add(ll);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return results;
    }

    public static Set<ArrayList> movePair (String line){
        Properties prop = new Properties();
        ArrayList<String> letter_set = new ArrayList<String>();
        ArrayList<ArrayList> array_set0 = new ArrayList<ArrayList>();
        Set<ArrayList> orderpairs = new HashSet<>();
        String firstPara = "";
        try{
            prop.load(ProcessPair.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            String ProcessPair_fileName = prop.getProperty("ProcessPair.filename");

            String[] s_line= line.split(":");
            firstPara = s_line[0];   //994
            String secondPara = s_line[1];   //set(['2'])set(['0', '5'])set(['1', '7'])set(['9', '8', '3', '4', '6'])
            String[] scd_set = secondPara.split("set");
            for(int i = 0; i < scd_set.length; i++){
                String[] unit = scd_set[i].split(",");  //(['2']),(['0', '5']...)
                letter_set = new ArrayList<String>();
                for (int j = 0; j < unit.length; j++){
                    String letter = StringFilter(unit[j]); // 0,5
                    letter_set.add(letter);
                }
                array_set0.add(letter_set); //[[2],[0,5],[1,7]...]
            }

            ArrayList<ArrayList> array_set = new ArrayList<ArrayList>();
            for (int n = 1; n < array_set0.size(); n++){
                array_set.add(array_set0.get(n));
            }
//        System.out.println(array_set);

            for (int k = 0; k < array_set.size(); k++){
                for (int p = 0; p < array_set.get(k).size(); p++){
                    for (int q = 0; q < array_set.get(k).size(); q++) {
                        if (array_set.get(k).get(p)!=array_set.get(k).get(q)) {
                            ArrayList<String> validcodes1 = new ArrayList<String>();
                            validcodes1.add(array_set.get(k).get(p).toString());
                            validcodes1.add(array_set.get(k).get(q).toString());
                            //System.out.println(validcodes);
                            orderpairs.add(validcodes1);
                        }
                    }
                }

            }
            for (int k = 0; k < array_set.size(); k++){
                for (int p = 0; p < array_set.get(k).size(); p++){
                    for (int q = k + 1; q < array_set.size(); q++) {
                        Iterator<String> iter = array_set.get(q).iterator();
                        while (iter.hasNext()) {
                            ArrayList<String> validcodes2 = new ArrayList<String>();
                            validcodes2.add(array_set.get(k).get(p).toString());
                            validcodes2.add(iter.next());
                            //System.out.println(validcodes);
                            orderpairs.add(validcodes2);

                        }
                    }
                }
            }
//        System.out.println(firstPara + ":" + orderpairs );
//        WriteFile.writeFileRepet(ProcessPair_fileName, firstPara, orderpairs);
        } catch (IOException e){
            e.printStackTrace();
        }
        return orderpairs;
    }


    private static String StringFilter(String article) throws PatternSyntaxException {
        // Only numbers and alphabet
        // String   regEx  =  "[^a-zA-Z0-9]";
        // Remove all the special characters
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(article);
        return   m.replaceAll(" ").trim();
    }


}
