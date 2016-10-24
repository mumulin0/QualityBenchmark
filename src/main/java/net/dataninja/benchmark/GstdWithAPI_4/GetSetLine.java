package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 2nd
 * output:2:set(['0', '7'])set(['5'])set(['9', '8', '4'])set(['1', '3', '2', '6'])
 * Choose the golden_std line from the golden standard sample file (spresult_amended2_golden.txt).
 * Store each set line in a gsSet.txt.
 */
public class GetSetLine {

	public ArrayList<String> getSetLine(String fileName){
		ArrayList<String> results = new ArrayList<String>();
		String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String article = "";
            String Golden_Std = "";
            String secondPara = "";
            String num = null;

            while((line = bufferedReader.readLine()) != null) {
                if( line.contains("-Original Article--")){
                	if(article.contains("Golden Std")){
                		results.add(num + ":" + secondPara); //+ "\r\n" + article);
                	}
                	num = line.substring(line.length() - 4);
                    num = num.replaceAll("-","");
                	article = "";
                    Golden_Std= "";
                }
                if(line.contains("Golden Std")){
                    Golden_Std = line + Golden_Std;
                    secondPara = Golden_Std.split("--->")[Golden_Std.split("--->").length - 1].trim();

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

    public ArrayList<String> getId(ArrayList<String> results){
        ArrayList<String> id_list = new ArrayList<String>();
        for (int i = 0; i < results.size(); i++){
            String id = results.get(i).split(":")[0];
            id_list.add(id);
        }
        return id_list;
    }

}
