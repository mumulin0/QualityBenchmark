/*
 * Copyright 2015 DOCOMO Innovations, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * See the License for the specific language governing permissions and limitations under the License.
 */

package net.dataninja.benchmark.SCClient_3;

import net.dataninja.smartcontent.client.*;
import net.dataninja.smartcontent.client.Input;
import net.dataninja.smartcontent.client.SmartContentApi;
import net.dataninja.smartcontent.client.SmartContentClient;

import java.io.*;
import java.util.*;

/**
 * 1. Call the API and do some modifies to control the concept output.(Modify the client.concept)
 * 2. Write a SCReadFiles class. Use .txt in FinalMatchGstd as input. (read directory by directory, line by line)
 * 3. Preprocess every concept list and store them in API_concept.txt.
 */
public class SCFetcher {

    /**
     * Run through the Smart Content APIs and show usage
     */
    private String process1(String input) {
        SmartContentApi client = new SmartContentClient().getClient();
        String idList = findConceptId(client, input);
        return idList;
    }

    // Service options can be con,cat,ee,ke,ks

    //concept id
    private String findConceptId(SmartContentApi client, String data) {
        String result = "";
        Input input = new Input();
        input.setMax_size(25);
        input.setText(data);

        List<Concept> conceptList = client.processSmartContent("con", input).getConcept_list();
        if (conceptList.size()==0){
            result += "]";
        }
        for (int i = 0; i < conceptList.size(); i++) {
            if (i == conceptList.size()-1){
                result += conceptList.get(i).getConcept_id() + "]";
            }
            else{
                result += conceptList.get(i).getConcept_id() + ", ";
            }
        }
        return result;
    }

    //Override the compare method to sort by int. to control the output index's order
    private static class Str2IntComparator implements Comparator<String> {
        private boolean reverseOrder; // reverse or not
        private Str2IntComparator(boolean reverseOrder) {
            this.reverseOrder = reverseOrder;
        }

        public int compare(String arg0, String arg1) {
            if(reverseOrder)
                return Integer.parseInt(arg1) - Integer.parseInt(arg0);
            else
                return Integer.parseInt(arg0) - Integer.parseInt(arg1);
        }
    }


    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();

        try {
            prop.load(SCFetcher.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            String SCFetcher_fileName_D = prop.getProperty("API_concept.filename");
            String SCFetcher_fileName_S = prop.getProperty("FinalMatchGstd.filename");

            SCFetcher demo = new SCFetcher();
            String name = "";
            Map map = new HashMap();
            //Use to sort the namepro
            List<String > list = new ArrayList<String>();


            File fout = new File(SCFetcher_fileName_D);
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            SCReadFiles SCReadFiles = new SCReadFiles();

            ArrayList<File> files = SCReadFiles.getListFiles(SCFetcher_fileName_S);
            for (int i = 0; i < files.size(); i++) {
                String article = SCReadFiles.getArticle(files.get(i));
                String idList = demo.process1(article);
                name = files.get(i).getName();
                String[] namePro = name.split("_");
                list.add(namePro[0]);
                map.put(namePro[0] , namePro[0] + ":" + idList);
            }
            Collections.sort(list,new Str2IntComparator(false));
            List<String> orderList = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                // According to the order in the List,obtain the key and stored it in orderList
                orderList.add(map.get(list.get(i)).toString());
            }

            for (String s : orderList) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
