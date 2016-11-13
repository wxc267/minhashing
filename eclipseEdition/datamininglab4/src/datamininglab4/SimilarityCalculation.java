package datamininglab4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimilarityCalculation {
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
