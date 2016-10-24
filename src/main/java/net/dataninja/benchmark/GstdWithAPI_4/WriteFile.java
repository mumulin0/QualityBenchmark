package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * writeFileRepet: It won't cover the original data.
 * Move the output(System.out.print()) to a .txt
 */
public class WriteFile {
    public static void writeFileRepet(String filename, String numStr, Set<ArrayList> results){
        try{
            File writename = new File(filename);
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename,true));
            out.write(numStr + ":" + results + "\r\n");
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileRepet(String filename, String numStr, ArrayList<?> results){
        try{
            File writename = new File(filename);
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename,true));
            out.write(numStr + ":" + results + "\r\n");
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String filename,ArrayList<?> results){
        try{
            File writename = new File(filename);
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for(int j =0; j < results.size(); j++){
                out.write(results.get(j) + "\r\n");
            }
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeFile(String filename,ArrayList<String> numStr,ArrayList<?> results){
        try{
            File writename = new File(filename);
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for(int j =0; j < results.size(); j++){
                out.write(numStr.get(j) + ": violation_num: " + results.get(j) + "\r\n");
            }
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
