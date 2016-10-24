package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by lindu on 10/3/2016.
 */

/**
 * 5th
 * Compared with GstdHash, change the output format from API_concept.txt(API)
 * {976=[[48233019], [1306419], [3410871]]}
 */

public class APIHash {

    public ArrayList<HashMap<String, ArrayList<Set>>> articleSets(String fileName){
        ArrayList<HashMap<String, ArrayList<Set>>> results = new ArrayList<HashMap<String, ArrayList<Set>>>();
        String line = null;
        String article = "";
        String firstPara = "";

        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                ArrayList<Set> eachArticle = new ArrayList<Set>();
                article += line;
                firstPara = article.split(":")[0];
                String secondPara = article.split(":")[1];
                String[] ss = StringFilter(secondPara).split("\\s+");
                for (int j = 0; j < ss.length; j++) {
                    Set eachSet = new HashSet();
                    eachSet.add(ss[j]);
//                    System.out.println(ss[j]);
                    eachArticle.add(eachSet);
                }
                article = "";
                HashMap<String, ArrayList<Set>> articleSet = new HashMap<>();
                articleSet.put(firstPara, eachArticle);
                results.add(articleSet);

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

    private static String StringFilter(String article) throws PatternSyntaxException   {
        // Only numbers and alphabet
        // String   regEx  =  "[^a-zA-Z0-9]";
        // Remove all the special characters
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(article);
        return   m.replaceAll(" ").trim();
    }
}
