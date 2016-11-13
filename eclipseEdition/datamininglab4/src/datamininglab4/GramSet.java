package datamininglab4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GramSet {
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
}
