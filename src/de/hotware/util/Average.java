/**
 * @author David Bauske (1242618), Martin Braun (1249080)
 */
package de.hotware.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for computing the average of a series of numbers.
 *
 * Credits for the original implementation go to
 * <a href="http://www.javaspecialists.eu/archive/Issue124.html">Heinz Kabutz</a>.
 */
public class Average {

	private List<Double> values = new ArrayList<Double>();

	/**
	 * Add a new value to the series. Changes the values returned by mean() and
	 * stddev().
	 *
	 * @param value The new value to add to the series.
	 */
	public void add(double value) {
		values.add(value);
	}

	/**
	 * Checks whether the add() method has been invoked yet.
	 *
	 * @return <i>true</i> if there is at least one element in the average series.
	 */
	public boolean hasElements() {
		return values.size() > 0;
	}

	/**
	 * Calculate and return the mean of the series of numbers. Throws an
	 * exception if this is called before the add() method.
	 *
	 * @return The mean of all the numbers added to the series.
	 * @throws IllegalStateException If no values have been added yet.
	 */
	public double mean() {
		int elements = values.size();
		if (elements == 0) {
			throw new IllegalStateException("No values in Average series.");
		}
		double sum = 0;
		for (double value : values) {
			sum += value;
		}
		return sum / elements;
	}

	/**
	 * Calculate and return the standard deviation of the series of numbers.
	 *
	 * @return the standard deviation of numbers added to the series.
	 * @throws IllegalStateException If no values have been added yet.
	 */
	public double standardDeviation() {
		double mean = mean();
		double stddevtotal = 0;
		for (double value : values) {
			double dev = value - mean;
			stddevtotal += dev * dev;
		}
		return Math.sqrt(stddevtotal / values.size());
	}
}