package datamininglab4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UtilClass {
	/**
	 *  convert the integer number to string number. 0 to 000
	 * @param number
	 * integer number
	 * @return
	 * string number
	 */
	public static String GetThreeDigitsNumber(int number)
	{
		String result="000";
		if(number<10&&number>=0)
		{
			result="00"+String.valueOf(number);
		}
		else if(number>=10&&number<100)
		{
			result="0"+String.valueOf(number);
		}
		else
		{
			result=String.valueOf(number);
		}
		return result;
	}
	/**
	 * parse the document and convert them into Reuter document
	 * @param fileName
	 * the name of file
	 * @return
	 * List of reuter document which contains infomation
	 */
	public static List<ReuterDoc> parse(String fileName)
	{
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		Document doc;
		try {
			
			doc = Jsoup.parse(new File(fileName), "UTF-8");
			Elements reuters=doc.select("REUTERS");
			int size=reuters.size();
			for(int i=0;i<size;i++)
			{
				Element reuter=reuters.get(i);
				ReuterDoc reuterDoc=new ReuterDoc();	
				
				Element title=reuter.select("TITLE").first();
				Element topics=reuter.select("TOPICS").first();
				Element text=reuter.select("TEXT").first();
				List<String> topicList=new ArrayList<String>();
				for(int j=0;j<topics.childNodeSize();j++)
				{
					Element topic=topics.child(j);
					topicList.add(topic.text());
				}

					reuterDoc.topics=topicList;
					reuterDoc.body=text.text();
					reuterList.add(reuterDoc);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return reuterList;
	}
	///////////////////////////////////////////////File parser//////////////////////////////////////////////////
	/**
	 * convert the document into n-grams
	 * @param body
	 * 			the text of a document
	 * @param n
	 * 			the number of the characters(grams)
	 * @return
	 * 			the set of gram
	 */
	public static Set<String> generateGramSet(String body,int n)
	{
		Set<String> gramSet=new HashSet<String>();
		int j=0;
		String gram="";
		for(int i=0;i<body.length();i++)
		{
			//put the character into gram
			char ch=body.charAt(i);
			if(Character.isLetter(ch))
			{
				ch=Character.toLowerCase(ch);
				gram+=ch;
				j++;
			}
			 if(j>=n||i==body.length()-1)
			{
				//put the n-gram into the set
				gramSet.add(gram);
				j=0;
				gram="";
			}
		}
		return gramSet;
	}
	//////////////////////////////////////////////////Gram set/////////////////////////////////////////////////
	/**
	 * Get gram set for all documents
	 * @param reuterList
	 * 					documents
	 * @param n
	 * 					number of character for each grams
	 * @return
	 * 				gram list
	 */
	public static List<Set<String>> generateGramList(List<ReuterDoc> reuterList,int n)
	{
		List<Set<String>> gramList=new ArrayList<Set<String>>();
		for(int i=0;i<reuterList.size();i++)
		{
			Set<String> grams=generateGramSet(reuterList.get(i).body,n);
			gramList.add(grams);
		}
		return gramList;
	}
	/**
	 *  generate universal gram set
	 * @param gramList
	 * 				list of gram
	 * @return
	 * 				set of universal gram
	 */
	public static Set<String> getUniverseGramSet(List<Set<String>> gramList)
	{
		Set<String> universe=new HashSet<String>();
		for(int i=0;i<gramList.size();i++)
		{
			universe.addAll(gramList.get(i));
		}
		return universe;
	}
	///////////////////////////////////////////////Boolean Matrics///////////////////////////////////
	/**
	 * convert shingles into boolean matrics.
	 * @param shingles
	 * 			the set of 3-word shingles for all documents.
	 * @param universe
	 * 			the set of universal shingle sets
	 * .
	 * @return
	 * 				boolean matrics
	 */
	public static List<List<Boolean>> getBooleanMatrics(List<Set<String>> shingles,Set<String> universe)
	{
		 List<List<Boolean>> matrics=new ArrayList<List<Boolean>>();
		 List<String> universalShingle=new ArrayList<String>(universe);
		 for(int i=0; i<shingles.size();i++)
		 {
			 List<Boolean> matrix=new ArrayList<Boolean>();
			 Set<String> set=shingles.get(i);
			 for(int j=0;j<universalShingle.size();j++)
			 {
				 String word=universalShingle.get(j);
				 if(set.contains(word))
				 {
					 matrix.add(true);
				 }
				 else
				 {
					 matrix.add(false);
				 }
			 }
			 matrics.add(matrix);
		 }
		 return matrics;
	}
	/////////////////////////////////////////////Base line Similarity/////////////////////////////////////////////////////
	/**
	 * calculate the true similarity of the shingle sets.
	 * @param shingleSet
	 * 					the shingle sets
	 * @return
	 * 				the true similarity of matrics.
	 */
	public static List<List<Double>> getBaseLineSimilarity(List<Set<String>> shingleSet)
	{
		List<List<Double>> result=new ArrayList<List<Double>>();
		for(int i=0;i<shingleSet.size();i++)
		{
			List<Double> similarity=new ArrayList<Double>();
			Set<String> document1=shingleSet.get(i);
			for(int j=0;j<i;j++)
			{
				Set<String> document2=shingleSet.get(j);
				Set<String> union=new HashSet<String>();
				Set<String> intersect=new HashSet<String>();
				union.addAll(document1);
				union.addAll(document2);
				intersect.addAll(document1);
				intersect.retainAll(document2);
				double jaccard=intersect.size()*1.0/union.size();
				similarity.add(jaccard);
			}
			result.add(similarity);
		}
		return result;
	}
	/**
	 * get the result of minhashing similarity
	 * @param signatureMatrix
	 * 
	 * @return
	 * 					min hashing similarity
	 */
	public static List<List<Double>> getMinHashingSimilarity(List<List<Integer>> signatureMatrix)
	{
		List<List<Double>> result=new ArrayList<List<Double>>();
		for(int i=0;i<signatureMatrix.size();i++)
		{
			List<Double> similarity=new ArrayList<Double>();
			List<Integer> signature=signatureMatrix.get(i);
			for(int j=0;j<i;j++)
			{
				List<Integer> signature2=signatureMatrix.get(j);
				int sim=0;
				for(int k=0;k<signature.size();k++)
				{
					if(signature.get(k)==signature2.get(k))
					{
						sim++;
					}
				}
				similarity.add(sim*1.0/signature.size());
			}
			result.add(similarity);
		}
		return result;
	}
	/**
	 * get mean squared error between the base line and sketch.
	 * @param baseline
	 * 					similarity of baseline
	 * @param minhashing
	 * 					similarity of min hashing
	 * @return
	 * 					quality of the min hashing.
	 */
	public static double calculateMSE(List<List<Double>> baseline,List<List<Double>> minhashing)
	{
		double sum=0;
		int count=0;
		for(int i=0;i<baseline.size();i++)
		{
			List<Double> trueSim=baseline.get(i);
			List<Double> estimateSim=minhashing.get(i);
			for(int j=0;j<trueSim.size();j++)
			{
				double trueValue=trueSim.get(j);
				double estimatedValue=estimateSim.get(j);
				sum+=Math.pow(estimatedValue-trueValue,2 );
				count++;
			}
		}
		double result=sum/count;
		return result;
	}
	
}
