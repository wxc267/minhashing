package datamininglab4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShingleVector {
/**
 * k-word shingles
 */
	private static int k=3;//3-word shingles
	/**
	 * get a shingle set for a document
	 * @param sequence
	 * 					word sequences of a document
	 * @return
	 * 				shingle set
	 */
	public static Set<String> getShingleSet(List<String> sequence)
	{
		Set<String> shingle=new HashSet<String>();
		for(int i=0;i<sequence.size()-k;i++)
		{
			String wordShingle="";
			for(int j=0;j<k;j++)
			{
				wordShingle=sequence.get(i+j)+" ";
			}
			shingle.add(wordShingle);
		}
		return shingle;
	}
	public static List<Set<String>> getShingleSets(List<List<String>> sequenceList)
	{
		 List<Set<String>> shingleList=new  ArrayList<Set<String>>();
		 for(int i=0;i<sequenceList.size();i++)
		 {
			 List<String> sequence=sequenceList.get(i);
			 Set<String> shingle=getShingleSet(sequence);
			 shingleList.add(shingle);
		 }
		 return shingleList;
	}
	public static Set<String> getUniverseShingleSet(List<List<String>> sequenceList)
	{
		Set<String> result=new HashSet<String>();
		 for(int i=0;i<sequenceList.size();i++)
		 {
			 List<String> sequence=sequenceList.get(i);
			 Set<String> shingle=getShingleSet(sequence);
			result.addAll(shingle);
		 }
		return result;
	}
	
}
