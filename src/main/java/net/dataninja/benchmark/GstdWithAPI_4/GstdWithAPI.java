package net.dataninja.benchmark.GstdWithAPI_4;

import java.io.IOException;
import java.util.*;

/**
 * This project is to process both the golden standard and the API's output.
 * Match the concept id and find the violations.
 */
public class GstdWithAPI {

	public static void main(String[] args) {
		Properties prop = new Properties();

		int option = 0; // This is the default option
		if (args.length > 0) {
			option = Integer.parseInt(args[0]);
			System.out.println("Option given: " + option);
		} else {
			System.out.println("Warning! Using default option 0");
			// System.exit(0);
		}

		switch (option) {
			case 2:
				GetSetLineMT(prop);
				break;
			case 3:
				RankingResultMT(prop);
			case 4:
				GstdHashMT(prop);
				break;
			case 5:
				APIHashMT(prop);
				break;
			case 6:
				ProcessPairMT(prop);
				break;
			case 7:
				ProcessPair_APIMT(prop);
				break;
			case 8:
				ComputeVioNumMT(prop);
				break;
			case 9:
				ViolationTotalNumMT(prop);
				break;
			default:
				System.out.print("2:Choose the golden_std line from the golden standard sample file and Store each set line in a gsSet.txt." + "\r\n" +
						"3:Print out the Search Result Rankings(0-9)." + "\r\n" +
						"4:Choose the articles which have golden standard and Combine the ranking index and concept id" + "\r\n" +
						"5:Compared with GstdHash, change the output format from API_concept.txt(API)." + "\r\n" +
						"6:Process the gsSet file. Obtain all the pairs." + "\r\n" +
						"7:Process the api(new) output and obtain all the pairs." + "\r\n" +
						"8:compute the violatio." + "\r\n" +
						"9:Compute the number of articles that has such violation number."
				);
				break;
		}
	}

/**
 * 1st
 *  Get the id number for all the following use.
 */
//		ArrayList<String> ID = new ArrayList<String>();
//		GetSetLine getid = new GetSetLine();
//		ArrayList<String> id_results = getid.getSetLine(GoldenStandard_fileName);
//		ID = getid.getId(id_results);

/**
 * 2nd
 * output:2:set(['0', '7'])set(['5'])set(['9', '8', '4'])set(['1', '3', '2', '6'])
 * Choose the golden_std line from the golden standard sample file (spresult_amended2_golden.txt).
 * Store each set line in a gsSet.txt.
  */
public static void GetSetLineMT(Properties prop) {
	try{
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String GoldenStandard_fileName = prop.getProperty("GoldenStandard.filename");
		String gsSet_fileName = prop.getProperty("gsSet.filename");
		GetSetLine goldenStd = new GetSetLine();
		ArrayList<String> gold_results = goldenStd.getSetLine(GoldenStandard_fileName);
		WriteFile.writeFile(gsSet_fileName, gold_results);
		for (int i = 0; i < gold_results.size(); i++) {
			System.out.println(gold_results.get(i));
		}
	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 * 3rd
 * Print out the Search Result Rankings(0-9)
 * 994----------Search Result Rankings----------
 */
public static void RankingResultMT(Properties prop) {
	try{
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String GoldenStandard_fileName = prop.getProperty("GoldenStandard.filename");
		String RankingResult_fileName = prop.getProperty("RankingResult.filename");
		RankingResult comparison = new RankingResult();
		ArrayList<String> com_results = comparison.nums(GoldenStandard_fileName);
		WriteFile.writeFile(RankingResult_fileName,com_results);
		for (int i = 0; i < com_results.size(); i++) {
			System.out.println(com_results.get(i));
		}
	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 * 4th
 * 1. Choose the articles which have golden standard.
 * 2. Combine the ranking index and concept id.
 * output:{976=[[0, 48233019], [1, 12251510], [2, 1269247], [3, 334276], [4, 9923141], [5, 3551205], [6, 1306419], [7, 14720412], [8, 3206374], [9, 391478]]}
 */
public static void GstdHashMT(Properties prop) {
	try{
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String GoldenStandard_fileName = prop.getProperty("GoldenStandard.filename");
		GstdHash segment = new GstdHash();
		ArrayList<HashMap<String, ArrayList<Set>>> segment_results = segment.nums(GoldenStandard_fileName);
		for (int j = 0; j < segment_results.size(); j++) {
			System.out.println(segment_results.get(j));
		}
	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 * 5th
 * Compared with GstdHash, change the output format from API_concept.txt(API)
 * {976=[[48233019], [1306419], [3410871]]}
 */
public static void APIHashMT(Properties prop) {
	try{
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String API_concept_fileName = prop.getProperty("API_concept.filename");
		String APIHash_fileName = prop.getProperty("APIHash.filename");

		APIHash apiHash = new APIHash();
		ArrayList<HashMap<String, ArrayList<Set>>> apiHash_results = apiHash.articleSets(API_concept_fileName);
		WriteFile.writeFile(APIHash_fileName,apiHash_results);

		for (int i = 0; i < apiHash_results.size(); i++) {
			System.out.println(apiHash_results.get(i));
		}
	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 * 6th
 * Process the gsSet file. Obtain all the pairs in the original following. 994:set(['2'])set(['0', '5'])set(['1', '7'])set(['9', '8', '3', '4', '6'])
 * Attention: number in the same set without order. [0,5],[5,0] are all legal.
 * [[3, 2], [4, 3], [2, 3], [0, 1], [1, 2], [0, 2], [1, 3], [0, 3], [1, 4], [0, 4], [4, 2]]
 */
public static void ProcessPairMT(Properties prop) {
	try {
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String gsSet_fileName = prop.getProperty("gsSet.filename");

		ProcessPair pairset = new ProcessPair();
		ArrayList<Set> pairset_results = pairset.Pairs(gsSet_fileName);
		double num = 0.0;
		ArrayList<Double> nums = new ArrayList<Double>();
		for (int i = 0; i < pairset_results.size(); i++) {
			System.out.println(pairset_results.get(i));
			num = pairset_results.get(i).size();
			nums.add(num);
		}
	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 * 7th
 * Process the api(new) output and obtain all the pairs in the following. 976:[48233019, 1306419, 3410871]
 * [[0, 1], [1, 2], [0, 2], [2, 6], [1, 6], [0, 6]]
 */
public static void ProcessPair_APIMT(Properties prop) {
	try{
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String API_concept_fileName = prop.getProperty("API_concept.filename");
		String GoldenStandard_fileName = prop.getProperty("GoldenStandard.filename");

		APIHash apiHash2 = new APIHash();
		GstdHash segment2 = new GstdHash();
		ArrayList<HashMap<String, ArrayList<Set>>> apiHash_results2 = apiHash2.articleSets(API_concept_fileName);
		ArrayList<HashMap<String, ArrayList<Set>>> segment_results2 = segment2.nums(GoldenStandard_fileName);

		ProcessPair_API pair_api = new ProcessPair_API();
		ArrayList<Set> pair_api_results = pair_api.Pairs_API(apiHash_results2,segment_results2);
		double num_api = 0.0;
		ArrayList<Double> nums_api = new ArrayList<Double>();
		for (int i = 0; i < pair_api_results.size(); i++) {
			System.out.println(pair_api_results.get(i));
			num_api = pair_api_results.get(i).size();
			nums_api.add(num_api);
		}
	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 *  8th
 * 	compute the violation. (need to run the 1st+6th+7th)
 */
public static void ComputeVioNumMT(Properties prop) {
	try{
		prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
		String GoldenStandard_fileName = prop.getProperty("GoldenStandard.filename");
		String API_concept_fileName = prop.getProperty("API_concept.filename");
		String gsSet_fileName = prop.getProperty("gsSet.filename");
		String violation_num_fileName = prop.getProperty("violation_num.filename");

		ArrayList<String> ID = new ArrayList<String>();
		GetSetLine getid = new GetSetLine();
		ArrayList<String> id_results = getid.getSetLine(GoldenStandard_fileName);
		ID = getid.getId(id_results);

		ProcessPair pairset = new ProcessPair();
		ArrayList<Set> pairset_results = pairset.Pairs(gsSet_fileName);
		double num = 0.0;
		ArrayList<Double> nums = new ArrayList<Double>();
		for (int i = 0; i < pairset_results.size(); i++) {
			System.out.println(pairset_results.get(i));
			num = pairset_results.get(i).size();
			nums.add(num);
		}
		APIHash apiHash2 = new APIHash();
		GstdHash segment2 = new GstdHash();
		ArrayList<HashMap<String, ArrayList<Set>>> apiHash_results2 = apiHash2.articleSets(API_concept_fileName);
		ArrayList<HashMap<String, ArrayList<Set>>> segment_results2 = segment2.nums(GoldenStandard_fileName);

		ProcessPair_API pair_api = new ProcessPair_API();
		ArrayList<Set> pair_api_results = pair_api.Pairs_API(apiHash_results2,segment_results2);
		double num_api = 0.0;
		ArrayList<Double> nums_api = new ArrayList<Double>();
		for (int i = 0; i < pair_api_results.size(); i++) {
			System.out.println(pair_api_results.get(i));
			num_api = pair_api_results.get(i).size();
			nums_api.add(num_api);
		}
		double result_violation = 0.0;
		ArrayList<Double> results_violation = new ArrayList<Double>();
		ArrayList<Integer> results_violation_num = new ArrayList<Integer>();

		for (int i = 0; i < pair_api_results.size(); i++){
			Iterator<ArrayList> iter = pair_api_results.get(i).iterator();
			double num_violation = 0;
			while (iter.hasNext()){
				if (!pairset_results.get(i).contains(iter.next())){
					num_violation = num_violation + 1;
				}
			}
			result_violation = num_violation / nums.get(i);
			results_violation.add(result_violation);
			results_violation_num.add((int)num_violation);

		}
		for (int i = 0; i < results_violation_num.size(); i++){
			System.out.println(ID.get(i) + ": " + "violation_num:" + results_violation_num.get(i));
			System.out.println(ID.get(i) + ": " + "violation_num:" + results_violation_num.get(i) + ", " + "violation_fraction:" + results_violation.get(i));
		}
		WriteFile.writeFile(violation_num_fileName,ID,results_violation_num);

	}catch (IOException e){
		e.printStackTrace();
	}
}

/**
 *  9th
 *  Compute the number of articles that has such violation number.
 */
	public static void ViolationTotalNumMT(Properties prop) {
		try{
            prop.load(GstdWithAPI.class.getClassLoader().getResourceAsStream("benchmark.properties"));
            String violation_num_fileName = prop.getProperty("violation_num.filename");
            ComputeVioNum computevionum = new ComputeVioNum();
            HashMap<String,Integer> computevionum_results = computevionum.computeVioNum(violation_num_fileName);
            System.out.print(computevionum_results);
        }catch (IOException e){
            e.printStackTrace();
        }
	}

}

