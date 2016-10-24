package net.dataninja.benchmark.SCClient_3;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lindu on 9/24/16.
 */
public class SCReadFiles {

    public String getArticle(File file){
        String article = "";
        String line = null;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                article = article + line;
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            file.getPath() + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + file.getPath() + "'");
        }
        return article;
    }

    public ArrayList<File> getListFiles(Object obj) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        ArrayList<File> files = new ArrayList<File>();

        if (directory.isFile()) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                files.addAll(getListFiles(fileOne));
            }
        }
        return files;
    }

}
