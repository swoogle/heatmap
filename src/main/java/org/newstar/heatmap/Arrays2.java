package org.newstar.heatmap;

import java.util.Arrays;
import java.util.Random;

public class Arrays2 {
	public static double calcAverage(double[] value) {
		double average = 0.0D;
		for (int i = 0; i < value.length; ++i)
			average += value[i];
		average /= value.length;
		return average;
	}

	public static double calcMaximum(double[] value) {
		double maximum = value[0];
		for (int i = 1; i < value.length; ++i)
			maximum = Math.max(maximum, value[i]);
		return maximum;
	}

	public static int calcMaximum(int[] value) {
		int maximum = value[0];
		for (int i = 1; i < value.length; ++i)
			maximum = Math.max(maximum, value[i]);
		return maximum;
	}

	public static double calcMedian(double[] value) {
		double[] sortedValue = (double[]) value.clone();
		Arrays.sort(sortedValue);
		double median;
		if (sortedValue.length % 2 == 1)
			median = sortedValue[((sortedValue.length - 1) / 2)];
		else
			median = (sortedValue[(sortedValue.length / 2 - 1)] + sortedValue[(sortedValue.length / 2)]) / 2.0D;
		return median;
	}

	public static double calcMinimum(double[] value) {
		double minimum = value[0];
		for (int i = 1; i < value.length; ++i)
			minimum = Math.min(minimum, value[i]);
		return minimum;
	}

	public static double calcSum(double[] value) {
		double sum = 0.0D;
		for (int i = 0; i < value.length; ++i)
			sum += value[i];
		return sum;
	}

	public static double calcSum(double[] value, int beginIndex, int endIndex) {
		double sum = 0.0D;
		for (int i = beginIndex; i < endIndex; ++i)
			sum += value[i];
		return sum;
	}

	public static int[] generateRandomPermutation(int nElements) {
		return generateRandomPermutation(nElements, new Random());
	}

	public static int[] generateRandomPermutation(int nElements, Random random) {
		int[] permutation = new int[nElements];
		for (int i = 0; i < nElements; ++i)
			permutation[i] = i;
		for (int i = 0; i < nElements; ++i) {
			int j = random.nextInt(nElements);
			int k = permutation[i];
			permutation[i] = permutation[j];
			permutation[j] = k;
		}
		return permutation;
	}
}