package net.dataninja.benchmark.FinalMatchGstd_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Choose the .txt from mapping-relation file which has the golden standard to FinalMatchGstd file.
 */
public class FinalMatchGstd {
    public static void main(String[] args) {

        ProcessFinalMatch matchStd = new ProcessFinalMatch();
        Properties prop = new Properties();
        try{
            prop.load(FinalMatchGstd.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            String FinalMatchGstd_fileName = prop.getProperty("GoldenStandard.filename");
            ArrayList<String> results = matchStd.FinalMatchIndex(FinalMatchGstd_fileName);
            //System.out.print(results);
            ArrayList<String> ans = matchStd.writeFinalMatchFile(results);
            ArrayList<String> fileoutput = matchStd.checkFinalMatchPrefix(ans);
            matchStd.handleFinalMatchFile(fileoutput);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}



