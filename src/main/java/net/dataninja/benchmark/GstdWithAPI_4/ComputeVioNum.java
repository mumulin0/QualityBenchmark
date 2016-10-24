package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *  9th
 *  Compute the number of articles that has such violation number.
 */

public class ComputeVioNum {
    public HashMap<String,Integer> computeVioNum(String fileName){
        HashMap<String,Integer> results= new HashMap<String,Integer>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\\s","");
                String num = line.split(":")[2];  //2:0   0
                int val=0;
                if(results.containsKey(num)){
                    val = results.get(num);
                    val++;
                }
                else{
                    val = 1;
                }
                results.put(num,val);
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
        return  results;
    }

}

