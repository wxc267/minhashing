package datamininglab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * this min hashing is with trick.
 * @author hl267
 *
 */
public class MinHashing {
/**
 *  the number of row
 */
	private int row;
	/**
	 * the number of random permutation
	 */
	private int k;
	/**
	 * boolean matrics
	 */
	private List<List<Integer>> booleanMatrics;
	/**
	 * hash function
	 */
	private List<List<Integer>> hashFunction;
	/**
	 * create min hashing function.
	 * @param k
	 * 					the number of random permutation
	 * @param booleanMatrics
	 * 					boolean Matrics
	 * @param row
	 * 				the number of rows;
	 */
	public MinHashing(int k,List<List<Integer>> booleanMatrics,int row)
	{
		this.k=k;
		hashFunction=new ArrayList<List<Integer>>();
		this.booleanMatrics=booleanMatrics;
		this.row=row;
		generateHashFunction();
	}
	private void generateHashFunction()
	{
		for(int i=0;i<k;i++)
		{
			List<Integer> random=new ArrayList<Integer>();
			Random rand1=new Random();
			int a=rand1.nextInt(row)+1;
			Random rand2=new Random();
			int b = rand2.nextInt(row)+2;
			random.add(a);
			random.add(b);
			hashFunction.add(random);
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
			List<Integer> indices=booleanMatrics.get(i);
			List<Integer> signature=new ArrayList<Integer>();
			for(int j=0;j<k;j++)
			{
				List<Integer> hash=hashFunction.get(j);
				int a=hash.get(0);
				int b=hash.get(1);
				int min=row;
				for(int l=0;l<indices.size();l++)
				{
					int index=indices.get(l);
					int value=(a*index+b) % row;
					if(value<min)
					{
						min=value;
						if(min==0)
						{
							break;
						}
					}
				}
				signature.add(min);
			}
			result.add(signature);
		}
		return result;
	}
}
