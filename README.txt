Text Analytics Quality Benchmark instruction
 
This project tries to implement text analytics quality benchmark. 
This project has 4 sub-projects. namely: MappingRelation_1, FinalMatchGstd_2, SCClient_3, GstdWithAPI_4. (numbers indicate the order of excution)

Fuctions for each sub-project:
MappingRelation_1: 
 * THis project focus on file.txt which is showing the mapping relationship between the golden standard file and the database file.(
 * 1. Preprocess this file.txt.
 * 2. Find the index of the original database file, choose all of them from diffbot file and store them in mapping-relation file.

FinalMatchGstd_2:
 * Choose the .txt from mapping-relation file which has the golden standard to FinalMatchGstd file.

SCClient_3:
 * 1. Call the API and do some modifies to control the concept output.(Modify the client.concept)
 * 2. Write a SCReadFiles class. Use .txt in FinalMatchGstd as input. (read directory by directory, line by line)
 * 3. Preprocess every concept list and store them in API_concept.txt.

GstdWithAPI_4:
 * This project is to process both the golden standard and the API's output.
 * Match the concept id and find the violations.
 * In this subproject, you can edit the configuration. Select the program arguments from 2-9 which indicate different outputs.
	2:  * output:2:set(['0', '7'])set(['5'])set(['9', '8', '4'])set(['1', '3', '2', '6'])
 	    * Choose the golden_std line from the golden standard sample file (spresult_amended2_golden.txt).
 	    * Store each set line in a gsSet.txt.
	3:  * Print out the Search Result Rankings(0-9)
 	    * 994----------Search Result Rankings----------
	4:  * Choose the articles which have golden standard.
                   * Combine the ranking index and concept id.
 	     * output:{976=[[0, 48233019], [1, 12251510], [2, 1269247], [3, 334276], [4, 9923141], [5, 3551205], [6, 1306419], [7, 14720412], [8, 3206374], [9, 391478]]}
	5:  * Compared with GstdHash, change the output format from API_concept.txt(API)
 	     * {976=[[48233019], [1306419], [3410871]]}
	6:  * Process the gsSet file. Obtain all the pairs in the original following. 994:set(['2'])set(['0', '5'])set(['1', '7'])set(['9', '8', '3', '4', '6'])
 	     * Attention: number in the same set without order. [0,5],[5,0] are all legal.
	     * [[3, 2], [4, 3], [2, 3], [0, 1], [1, 2], [0, 2], [1, 3], [0, 3], [1, 4], [0, 4], [4, 2]]
	7:  * Process the api(new) output and obtain all the pairs in the following. 976:[48233019, 1306419, 3410871] 
 	     * [[0, 1], [1, 2], [0, 2], [2, 6], [1, 6], [0, 6]]
	8:   * compute the violation.
	9:   *  Compute the number of articles that has such violation number.

Quick Start:
1. you can simply run it in IntelliJ (modify the resources: benchmark.properties to control your own input and output paths) .
2.Use command line: 
   mvn install:install-file -Dfile=C:\Users\lindu\IdeaProjects\dataninja-smartcontent-api-sdk-java-master\target\SmartContentClient-1.0-SNAPSHOT-jar-with-dependencies.jar -DgroupId=SmartContentClient -DartifactId=SmartContentClient -Dversion=1.0-SNAPSHOT -Dpackaging=jar 
   java -cp C:\Users\lindu\IdeaProjects\QualityBenchmark\target\QualityBenchmark-1.0-SNAPSHOT-jar-with-dependencies.jar net.dataninja.benchmark.$packagename.$mainclassname
  (for GstdWithAPI_4, you can add a parameter(2-9) to indicate which method you want to run:         java -cp C:\Users\lindu\IdeaProjects\QualityBenchmark\target\QualityBenchmark-1.0-SNAPSHOT-jar-with-dependencies.jar net.dataninja.benchmark.GstdWithAPI_4.GstdWithAPI 2)
