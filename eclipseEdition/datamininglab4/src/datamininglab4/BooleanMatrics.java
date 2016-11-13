package datamininglab4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooleanMatrics {
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
}
