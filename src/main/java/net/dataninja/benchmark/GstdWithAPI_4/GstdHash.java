package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 4th
 * 1. Choose the articles which have golden standard.
 * 2. Combine the ranking index and concept id.
 * output:{976=[[0, 48233019], [1, 12251510], [2, 1269247], [3, 334276], [4, 9923141], [5, 3551205], [6, 1306419], [7, 14720412], [8, 3206374], [9, 391478]]}
 */

public class GstdHash {

	public ArrayList<HashMap<String, ArrayList<Set>>> nums(String fileName){
		ArrayList<HashMap<String, ArrayList<Set>>> results = new ArrayList<HashMap<String, ArrayList<Set>>>();
		String line = null;
		Properties prop = new Properties();

		try {
			prop.load(GstdHash.class.getClassLoader().getResourceAsStream("benchmark.properties"));
			String GstdHash_fileName = prop.getProperty("GstdHash.filename");

			FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

			String article = "";
            String num = null;

			while((line = bufferedReader.readLine()) != null) {
                if( line.contains("-Original Article--")){
                	if(article.contains("Golden Std")){
//                		results.add(num + "\r\n" + Golden_Std); //+ "\r\n" + article);
                		ArrayList<Set> eachArticle = new ArrayList<Set>();
						ArrayList<String> results2 = new ArrayList<String>();
                		int ranking = article.indexOf("Search Result Rankings");
                		String rankingStr = article.substring(ranking);
                		String[] rankingResult = rankingStr.split("\r\n");
                		for(int jj = 1; jj < rankingResult.length; jj++){
//                			System.out.println(rankingResult[jj]);
                			String firstPara = rankingResult[jj].split(":")[0];
                			String secondPara = rankingResult[jj].split("--->")[rankingResult[jj].split("--->").length - 1];
                			Set eachSet = new LinkedHashSet();
                			eachSet.add(firstPara);
                			eachSet.add(secondPara);
                			eachArticle.add(eachSet);
							results2.add(secondPara) ;
                		}
                		HashMap<String, ArrayList<Set>> articleSet = new HashMap<>();
                		articleSet.put(num, eachArticle);
                		results.add(articleSet);
						// For the GstdHash.txt output  eg 2:[42526108, 13342861, 618376, 38932925, 42968661, 1585112, 1158171, 427707, 27170, 21719]

//						System.out.println(num + ":" + results2);
//						WriteFile.writeFileRepet(GstdHash_fileName,num,results2);
                	}
                	num = line.substring(line.length() - 4);
                    num = num.replaceAll("-","");
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

}
