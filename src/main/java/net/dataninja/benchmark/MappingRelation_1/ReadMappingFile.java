package net.dataninja.benchmark.MappingRelation_1;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 1. Read the mapping-relation file.txt line by line and parse the file by "," and ":" (1: 28866, 2: 44133, 3: 36271..)     ----parseMappingFile
 * 2. Check the mapping files exist  in the database(diffbot) or not.     ----checkMappingFile
 * 3. Move the mapping files from diffbot(database) to dest(destination).   ----handleMappingFile
 */
public class ReadMappingFile {

    String ReadMappingfileName_S = "";
    String ReadMappingfileName_D = "";
    public ArrayList<String> parseMappingFile(String fileName){
        ArrayList<String> results = new ArrayList<String>();
        String line = null;
        try {
            FileReader fileReader =
                    new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\\s+","");
                String[] nameWithNum = line.split(",");  //1: 28866
                for( int j = 0; j < nameWithNum.length; j++){
                    String[] name = nameWithNum[j].split(":");
                    results.add(name[1]);  //28866
                }
                //System.out.println(line);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open the mapping-relation  file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading the mapping-relation file '"
                            + fileName + "'");
        }
        return  results;
    }

    public void handleMappingFile(ArrayList<String> results){
        Properties prop = new Properties();

        try{
            prop.load(ReadMappingFile.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            ReadMappingfileName_S = prop.getProperty("diffbot.filename");
            ReadMappingfileName_D = prop.getProperty("mapping-relation.filename");

            for( int i = 0; i < results.size(); i++){

                if( checkMappingFile(results.get(i)) ){
                    int ii = i + 1;
                    File source = new File(ReadMappingfileName_S + "\\" + results.get(i)+".txt");
                    File mkdest = new File(ReadMappingfileName_D);
                    if(!mkdest.exists() && !mkdest.isDirectory()){
                        mkdest.mkdir();
                    }
                    File dest = new File(ReadMappingfileName_D + "\\" + ii + "_" + results.get(i) + ".txt");

                    try {
                        Files.copy(source.toPath(), dest.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private boolean checkMappingFile(String name){
        boolean result = false;
        Properties prop1 = new Properties();
        try{
            prop1.load(ReadMappingFile.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            ReadMappingfileName_S = prop1.getProperty("diffbot.filename");
        } catch (IOException e){
            e.printStackTrace();
        }
        //check file exists
        File f = new File(ReadMappingfileName_S + "\\" + name + ".txt");
        if(f.exists() && !f.isDirectory()) {
            result = true;
        }
        return result;
    }
}
