package datamininglab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author xw
 * generate the word sequence for each document text
 */
public class WordVector {
	/**
	 *  convert a single document to word sequence
	 * @param body
	 * 					The text body of the document
	 * @return
	 * 					word sequence
	 */
	public static List<String> convertToWordSequence(String body)
	{
		List<String> sequence=new ArrayList<String>();
		int start=0;
		int end=0;
		while(end<body.length())
		{
			//tokenize and count the word
			char ch=body.charAt(end);
			if(Character.isLetter(ch))
			{
				//if current one is character
				end++;
			}
			else if(Character.isLetter(body.charAt(start)))
			{
				/*
				if the begin of index of the string is character but the current index is non-character, then take
				the word. If the word is not stopwords, then 
				*/
				String word=body.substring(start,end);
				word=word.toLowerCase();
				sequence.add(word);
				start=end+1;
				end=start;
			}
			else
			{
				//if there are continuous non-letter characters.
				start++;
				end=start;
			}
		}
		return sequence;
	}
	/**
	 * convert the reuter document list to the word sequence list
	 * @param reuterList
	 * @return
	 * 				word sequence list
	 */
	public static List<List<String>> convertToWordSequences(List<ReuterDoc> reuterList)
	{
		List<List<String>> sequenceList=new ArrayList<List<String>>();
		for(int i=0;i<reuterList.size();i++)
		{
			List<String> sequence=convertToWordSequence(reuterList.get(i).body);
			sequenceList.add(sequence);
		}
		return sequenceList;
	}

}
