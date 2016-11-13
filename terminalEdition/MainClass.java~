package datamininglab4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;




public class MainClass {
	/**
	 * the number of grams.
	 */
	private static final int N=5;
	/**
	 * The K value as for K-minHashing
	 */
	private static final int[] K={16,32,64,128,256};

	public static void main(String[] args) throws IOException
	{
		//parsing
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		System.out.println("parsing....");
		for(int i=0;i<1;i++){
			String fileName="./data/reut2-"+UtilClass.GetThreeDigitsNumber(i)+".sgm";
			List<ReuterDoc> reuters=UtilClass.parse(fileName);
			//String example="./data/example";
			//List<ReuterDoc> reuters=UtilClass.parse(example);
			reuterList.addAll(reuters);
		}
		//transfer the body to shingling
		System.out.println("shingling....");
		long base_start=System.currentTimeMillis();
		List<Set<String>> shingleSet=UtilClass.generateGramList(reuterList,N);
		Set<String> universalSet=UtilClass.getUniverseGramSet(shingleSet);
		List<List<Double>> baseLine=UtilClass.getBaseLineSimilarity(shingleSet);
		long base_end=System.currentTimeMillis();
		double base_time=(base_end-base_start)*1.0/1000;
		System.out.println("The time of building up base line is "+base_time+"s.");
		//min hashing and calculate its quality.
		System.out.println("Hashing.....");
		List<List<Boolean>> booleanMatrics= UtilClass.getBooleanMatrics(shingleSet, universalSet);
		for(int i=0;i<K.length;i++){
			long start=System.currentTimeMillis();
			MinHashing min=new MinHashing(K[i],booleanMatrics);
			List<List<Integer>> signatureMatrix=min.generateSignatureMatrix();
			List<List<Double>> minHashing=UtilClass.getMinHashingSimilarity(signatureMatrix);	
			long end=System.currentTimeMillis();
			double result=UtilClass.calculateMSE(baseLine, minHashing);
			double time=(end-start)*1.0/1000;
			System.out.println("When k is " + K[i]+ ", the mean sqaure error is : "+result+", and it spends "+time+"s.");
		}
	}

}
