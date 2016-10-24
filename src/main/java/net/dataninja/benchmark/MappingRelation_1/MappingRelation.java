package net.dataninja.benchmark.MappingRelation_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by LinDu on 9/17/2016.
 */

/**
 * THis project focus on file.txt which is showing the mapping relationship between the golden standard file and the database file.
 * 1. Preprocess this file.txt.
 * 2. Find the index of the original database file, choose all of them from diffbot file and store them in mapping-relation file.
 */
public class MappingRelation {

    public static void main(String[] args){
        ReadMappingFile readMappingFile = new ReadMappingFile();
        Properties prop = new Properties();
        try {
            prop.load(MappingRelation.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            String MappingRelation_fileName = prop.getProperty("MappingRelation.filename");
            ArrayList<String> results = readMappingFile.parseMappingFile(MappingRelation_fileName);
            readMappingFile.handleMappingFile(results);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
