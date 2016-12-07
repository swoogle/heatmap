package org.newstar.heatmap;

public class DensityVisualization implements Cloneable {
	public static final int DENSITY_VIEW = 0;
	public static final int CLUSTER_DENSITY_VIEW = 1;
	public static final int BLUE_GREEN_RED = 0;
	public static final int WHITE_BLUE_PURPLE = 1;
	public static final int WHITE_GRAY_BLACK = 2;
	public static final int GRID_SIZE = 250;
	public static final double ITEM_DENSITY_THRESHOLD = 0.0001D;
	public static final double MIN_RELATIVE_CLUSTER_WEIGHT = 0.02D;
	public static final int DENSITY_VIEW_VISUALIZATION = 0;
	public static final double KERNEL_WIDTH = 0.12D;
	public static final double COLOR_TRANSFORMATION = 0.55D;
	public static final int COLOR_SCHEME = 0;
	public static final boolean USE_WHITE_BACKGROUND_CLUSTER_DENSITY_VIEW = false;
	private int gridSize;
	private double itemDensityThreshold;
	private double minRelativeClusterWeight;
	private int densityViewVisualization;
	private double kernelWidth;
	private double colorTransformation;
	private int colorScheme;
	private boolean useWhiteBackgroundClusterDensityView;

	public DensityVisualization() {
		this.gridSize = GRID_SIZE;
		this.itemDensityThreshold = ITEM_DENSITY_THRESHOLD;
		this.minRelativeClusterWeight = MIN_RELATIVE_CLUSTER_WEIGHT;

		this.densityViewVisualization = DENSITY_VIEW_VISUALIZATION;
		this.kernelWidth = KERNEL_WIDTH;
		this.colorTransformation = COLOR_TRANSFORMATION;
		this.colorScheme = COLOR_SCHEME;
		this.useWhiteBackgroundClusterDensityView = USE_WHITE_BACKGROUND_CLUSTER_DENSITY_VIEW;
	}

	public DensityVisualization clone() {
		DensityVisualization clone;
		try {
			clone = (DensityVisualization) super.clone();
		} catch (CloneNotSupportedException localCloneNotSupportedException) {
			return null;
		}
		return clone;
	}

	public int getColorScheme() {
		return this.colorScheme;
	}

	public double getColorTransformation() {
		return this.colorTransformation;
	}

	public int getDensityViewVisualization() {
		return this.densityViewVisualization;
	}

	public int getGridSize() {
		return this.gridSize;
	}

	public double getItemDensityThreshold() {
		return this.itemDensityThreshold;
	}

	public double getKernelWidth() {
		return this.kernelWidth;
	}

	public double getMinRelativeClusterWeight() {
		return this.minRelativeClusterWeight;
	}

	public boolean getUseWhiteBackgroundClusterDensityView() {
		return this.useWhiteBackgroundClusterDensityView;
	}

	public void setColorScheme(int colorScheme) {
		this.colorScheme = colorScheme;
	}

	public void setColorTransformation(double colorTransformation) {
		this.colorTransformation = colorTransformation;
	}

	public void setDensityViewVisualization(int densityViewVisualization) {
		this.densityViewVisualization = densityViewVisualization;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public void setItemDensityThreshold(double itemDensityThreshold) {
		this.itemDensityThreshold = itemDensityThreshold;
	}

	public void setKernelWidth(double kernelWidth) {
		this.kernelWidth = kernelWidth;
	}

	public void setMinRelativeClusterWeight(double minRelativeClusterWeight) {
		this.minRelativeClusterWeight = minRelativeClusterWeight;
	}

	public void setUseWhiteBackgroundClusterDensityView(
			boolean useWhiteBackgroundClusterDensityView) {
		this.useWhiteBackgroundClusterDensityView = useWhiteBackgroundClusterDensityView;
	}
}