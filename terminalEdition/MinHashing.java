
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHashing {
	/**
	 * the number of random permutation
	 */
	private int k;
	/**
	 * boolean matrics
	 */
	private List<List<Boolean>> booleanMatrics;
	/**
	 * random permutaion
	 */
	private List<List<Integer>> randomPermutation;
	/**
	 * create min hashing function.
	 * @param k
	 * 					the number of random permutation
	 * @param booleanMatrics
	 * 					boolean Matrics
	 */
	public MinHashing(int k,List<List<Boolean>> booleanMatrics)
	{
		this.k=k;
		this.booleanMatrics=booleanMatrics;
		 randomPermutation=new ArrayList<List<Integer>>();
		getRandomPermutationList();
	}
	private void getRandomPermutationList()
	{
		List<Integer> origin=new ArrayList<Integer>();
		int size=booleanMatrics.get(0).size();//get the number of the rows
		for(int i=0;i<size;i++)
		{
			origin.add(i);
		}
		for(int i=0;i<k;i++)
		{
			List<Integer> permutation=new ArrayList<Integer>(origin);
			Collections.shuffle(permutation);
			randomPermutation.add(permutation);
		}
	}
	/**
	 * generate the signature matrix for all document.
	 * 
	 * @return
	 * 		signature matrix for all document
	 */
	public List<List<Integer>> generateSignatureMatrix()
	{
		List<List<Integer>> result=new ArrayList<List<Integer>>();
		for(int i=0;i<booleanMatrics.size();i++)
		{
			List<Integer> signature=new ArrayList<Integer>();
			List<Boolean> matrix=booleanMatrics.get(i);
			for(int j=0;j<randomPermutation.size();j++)
			{
				List<Integer> permutation=randomPermutation.get(j);
				int min_index=permutation.size();
				for(int k=0;k<permutation.size();k++)
				{
					int index=permutation.get(k);
					if(matrix.get(index)&&index<min_index)
					{
						min_index=permutation.get(index);
					}
					if(min_index==1)
					{
						break;
					}
				}
				signature.add(min_index);
			}
			result.add(signature);
		}
		return result;
	}
}
