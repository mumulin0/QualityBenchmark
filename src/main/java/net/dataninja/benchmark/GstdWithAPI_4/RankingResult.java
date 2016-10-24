package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 3rd
 * Print out the Search Result Rankings(0-9)
 * 994----------Search Result Rankings----------
 */
public class RankingResult {
    public ArrayList<String> nums(String fileName){
        ArrayList<String> results = new ArrayList<String>();
        String line = null;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            String article = "";
            String ranking = "";
            String num = null;
            while((line = bufferedReader.readLine()) != null) {
                if( line.contains("-Original Article--")){
                    if(article.contains("Golden Std")){
                        results.add(num + "\r\n" + ranking); //+ "\r\n" + article);
                    }
                    num = line.substring(line.length() - 4);
                    num = num.replaceAll("-","");
                    article = "";
                    ranking = "";
                }
                if(line.contains("Search Result Rankings")){
                    ranking = num;
                }
                article += (line + "\r\n");
                if(ranking != null);{
                    ranking += (line + "\r\n");
                }
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
}
