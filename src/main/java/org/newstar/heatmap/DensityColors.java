package org.newstar.heatmap;

import java.awt.Color;
import java.util.Collections;
import java.util.Vector;

public class DensityColors implements Cloneable {
	public static final double[] DEFAULT_COLOR_VALUES = { 0.0D, 0.25D, 0.5D,
			0.75D, 1.0D };
	public static final Color[] DEFAULT_COLORS = { new Color(0, 0, 255),
			new Color(0, 255, 255), new Color(0, 255, 0),
			new Color(255, 255, 0), new Color(255, 0, 0) };
	public static final String COLOR_VALUE_COLUMN_HEADER = "color value";
	public static final String RED_COLUMN_HEADER = "red";
	public static final String GREEN_COLUMN_HEADER = "green";
	public static final String BLUE_COLUMN_HEADER = "blue";
	private Vector<Double> defaultColorValues;
	private Vector<Color> defaultColors;
	private Vector<Double> colorValues;
	private Vector<Color> colors;

	public DensityColors() {
		initDefaultColorScheme();
		restoreDefaultColorScheme();
	}

	@SuppressWarnings("unchecked")
	public DensityColors clone() {
		DensityColors clone;
		try {
			clone = (DensityColors) super.clone();
			clone.defaultColorValues = ((Vector<Double>) this.defaultColorValues
					.clone());
			clone.defaultColors = ((Vector<Color>) this.defaultColors.clone());
			clone.colorValues = ((Vector<Double>) this.colorValues.clone());
			clone.colors = ((Vector<Color>) this.colors.clone());
		} catch (CloneNotSupportedException localCloneNotSupportedException) {
			return null;
		}
		return clone;
	}

	public Color getColor(double density) {
		if (density <= ((Double) this.colorValues.firstElement()).doubleValue())
			return ((Color) this.colors.firstElement());
		if (density >= ((Double) this.colorValues.lastElement()).doubleValue())
			return ((Color) this.colors.lastElement());
		int i = Collections.binarySearch(this.colorValues,
				Double.valueOf(density));
		if (i < 0)
			i = -i - 1;
		double a = (density - ((Double) this.colorValues.get(i - 1))
				.doubleValue())
				/ (((Double) this.colorValues.get(i)).doubleValue() - ((Double) this.colorValues
						.get(i - 1)).doubleValue());
		return new Color((int) ((1.0D - a)
				* ((Color) this.colors.get(i - 1)).getRed() + a
				* ((Color) this.colors.get(i)).getRed()), (int) ((1.0D - a)
				* ((Color) this.colors.get(i - 1)).getGreen() + a
				* ((Color) this.colors.get(i)).getGreen()), (int) ((1.0D - a)
				* ((Color) this.colors.get(i - 1)).getBlue() + a
				* ((Color) this.colors.get(i)).getBlue()));
	}

	private void initDefaultColorScheme() {
		this.defaultColors = new Vector<Color>(DEFAULT_COLORS.length);
		this.defaultColorValues = new Vector<Double>(DEFAULT_COLORS.length);
		for (int i = 0; i < DEFAULT_COLORS.length; ++i) {
			this.defaultColors.add(DEFAULT_COLORS[i]);
			this.defaultColorValues
					.add(Double.valueOf(DEFAULT_COLOR_VALUES[i]));
		}
	}

	@SuppressWarnings("unchecked")
	public void restoreDefaultColorScheme() {
		this.colors = ((Vector<Color>) this.defaultColors.clone());
		this.colorValues = ((Vector<Double>) this.defaultColorValues.clone());
	}

}