package net.dataninja.benchmark.FinalMatchGstd_2;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 1. Find the corresponding index from the original golden standard file.(-----------Original Article----------------2)    ----FinalMatchIndex
 * 2. Filter out the name by the corresponding index.    ----checkFinalMatchPrefix
 * 3. Check the file(.txt) exist in mapping-relation file or not.    ----checkFinalMatchFile
 * 4. Move files from mapping-relation file(dir) to FinalMatchGstd file(dir)
 */
public class ProcessFinalMatch {

    String ProcessFinalMatchfileName_S = "";
    String ProcessFinalMatchfileName_D = "";

    public ArrayList<String> FinalMatchIndex(String fileName){
        ArrayList<String> results = new ArrayList<String>();
        String line = null;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            String article = "";
            String num = null;
            while((line = bufferedReader.readLine()) != null) {
                if( line.contains("-Original Article--")){
                    if(article.contains("Golden Std")){
                        results.add(num);
                    }
                    num = line.substring(line.length() - 4);
                    article = "";
                }
                article += (line+"\r\n");

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

    // Get all the ____ nums from the results and return an arrayList ans[1_,2_....]
    public ArrayList<String> writeFinalMatchFile(ArrayList<String> results){
        ArrayList<String> ans = new ArrayList<String>();
        for(int i = 0 ; i < results.size(); i++){
            String s = results.get(i);
            s = s.replaceAll("-","");
            s = s + '_';
            ans.add(s);
        }
        return ans;
        //System.out.print(ans);
    }

    // move file to dest folder
    public void handleFinalMatchFile(ArrayList<String> fileoutput){
        Properties prop = new Properties();

        try{
            prop.load(ProcessFinalMatch.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            ProcessFinalMatchfileName_S = prop.getProperty("mapping-relation.filename");
            ProcessFinalMatchfileName_D = prop.getProperty("FinalMatchGstd.filename");

            for( int i = 0; i < fileoutput.size(); i++){
                if( checkFinalMatchFile(fileoutput.get(i))){
                    File source = new File(ProcessFinalMatchfileName_S + "\\" + fileoutput.get(i));
                    File mkdest = new File(ProcessFinalMatchfileName_D);
                    if(!mkdest.exists() && !mkdest.isDirectory()){
                        mkdest.mkdir();
                    }
                    File dest = new File(ProcessFinalMatchfileName_D + "\\" + fileoutput.get(i));
                    try {
                        Files.copy(source.toPath(), dest.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();

        }

    }

    //Use list() method which exist in File to implement the accept() in order to filter out the name
    public ArrayList<String> checkFinalMatchPrefix(ArrayList<String> ans){
        ArrayList<String> fileoutput = new ArrayList<String>();
        Properties prop2 = new Properties();
        try{
            prop2.load(ProcessFinalMatch.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            ProcessFinalMatchfileName_S = prop2.getProperty("mapping-relation.filename");
        } catch (IOException e){
            e.printStackTrace();
        }

        File file = new File(ProcessFinalMatchfileName_S);
            File[] f = file.listFiles(new FileFilter(){
                @Override
                public boolean accept(File ff) {
                    for( int i = 0; i < ans.size(); i++) {
                        if (ff.getName().startsWith(ans.get(i))) {
                            return true;
                        }
                    }
                        return false;
                }
            });
            for (File g:f){
                fileoutput.add((g.getName()));
            }
        return fileoutput;
    }
        //System.out.println(fileoutput);


    private boolean checkFinalMatchFile(String name){
        boolean result = false;
        Properties prop3 = new Properties();
        try{
            prop3.load(ProcessFinalMatch.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            ProcessFinalMatchfileName_S = prop3.getProperty("mapping-relation.filename");
        } catch (IOException e){
            e.printStackTrace();
        }
        //check file exists
        File f = new File(ProcessFinalMatchfileName_S + "\\" + name);
        if(f.exists() && !f.isDirectory()) {
            // do something
            result = true;
        }
        return result;
    }
}
