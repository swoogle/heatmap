package org.newstar.heatmap;

public class HeatMapParameters {
	public static final double MAP_BORDER_SIZE = 0.1D;
	public static final double MIN_SCALING = 1.0D;
	public static final double MAX_SCALING = 10000.0D;
	public static final double INIT_SCALING = 1.0D;

	public static int logicalUnitsToPixels(double logicalUnits,
			double scalingFactor) {
		return (int) (scalingFactor * logicalUnits + 0.5D);
	}

	public static double pixelsToLogicalUnits(int pixels, double scalingFactor) {
		return (pixels / scalingFactor);
	}

	private double minScalingFactor;
	private double scalingFactor;
	private double overviewScalingFactor;
	private double xMin;
	private double yMin;
	private double xMax;
	private double yMax;
	private double xLeft;
	private double yTop;

	private int pixelWidth;

	private int pixelHeight;

	public double calcLowestScalingFactor(double xCenter, double yCenter) {
		double lowestScalingFactor = this.minScalingFactor;
		lowestScalingFactor = Math.max(lowestScalingFactor, 0.5D
				* this.pixelWidth / (xCenter - this.xMin));
		lowestScalingFactor = Math.max(lowestScalingFactor, 0.5D
				* this.pixelWidth / (this.xMax - xCenter));
		lowestScalingFactor = Math.max(lowestScalingFactor, 0.5D
				* this.pixelHeight / (yCenter - this.yMin));
		lowestScalingFactor = Math.max(lowestScalingFactor, 0.5D
				* this.pixelHeight / (this.yMax - yCenter));
		return lowestScalingFactor;
	}

	private void checkXLeft() {
		this.xLeft = Math.max(this.xLeft, this.xMin);
		this.xLeft = Math.min(this.xLeft, this.xMax - getWidth());
	}

	private void checkYTop() {
		this.yTop = Math.max(this.yTop, this.yMin);
		this.yTop = Math.min(this.yTop, this.yMax - getHeight());
	}

	public double getHeight() {
		return pixelsToLogicalUnits(this.pixelHeight);
	}

	public double getMinScalingFactor() {
		return this.minScalingFactor;
	}

	public double getScalingFactor() {
		return this.scalingFactor;
	}

	public double getWidth() {
		return pixelsToLogicalUnits(this.pixelWidth);
	}

	public double getXCenter() {
		return (this.xLeft + 0.5D * getWidth());
	}

	public double getXLeft() {
		return this.xLeft;
	}

	public double getXMax() {
		return this.xMax;
	}

	public double getXMin() {
		return this.xMin;
	}

	public double getYCenter() {
		return (this.yTop + 0.5D * getHeight());
	}

	public double getYMax() {
		return this.yMax;
	}

	public double getYMin() {
		return this.yMin;
	}

	public double getYTop() {
		return this.yTop;
	}

	public void init(double[] minValues, double[] maxValues, int pixelWidth,
			int pixelHeight, int overviewPixelWidth, int overviewPixelHeight) {
		double xItemMin = minValues[0];
		double yItemMin = -maxValues[1];
		double xItemMax = maxValues[0];
		double yItemMax = -minValues[1];
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		double minMapWidth = 1.2D * (xItemMax - xItemMin);
		double minMapHeight = 1.2D * (yItemMax - yItemMin);
		this.minScalingFactor = Math.min(pixelWidth / minMapWidth, pixelHeight
				/ minMapHeight);
		this.xMin = (xItemMin - (MAP_BORDER_SIZE * (xItemMax - xItemMin)) - (0.5D * (pixelsToLogicalUnits(
				pixelWidth, this.minScalingFactor) - minMapWidth)));
		this.yMin = (yItemMin - (MAP_BORDER_SIZE * (yItemMax - yItemMin)) - (0.5D * (pixelsToLogicalUnits(
				pixelHeight, this.minScalingFactor) - minMapHeight)));
		this.xMax = (this.xMin + pixelsToLogicalUnits(pixelWidth,
				this.minScalingFactor));
		this.yMax = (this.yMin + pixelsToLogicalUnits(pixelHeight,
				this.minScalingFactor));
		setScaling(INIT_SCALING);
		this.overviewScalingFactor = (Math.max(overviewPixelWidth / pixelWidth,
				overviewPixelHeight / pixelHeight) * this.minScalingFactor);
	}

	public int logicalUnitsToOverviewPixels(double logicalUnits) {
		return (int) (this.overviewScalingFactor * logicalUnits + 0.5D);
	}

	public int logicalUnitsToPixels(double logicalUnits) {
		return (int) (this.scalingFactor * logicalUnits + 0.5D);
	}

	public double overviewPixelsToLogicalUnits(int overviewPixels) {
		return (overviewPixels / this.overviewScalingFactor);
	}

	public void pan(double xDistance, double yDistance) {
		setXLeft(this.xLeft - xDistance);
		setYTop(this.yTop - yDistance);
	}

	public void pan(double xDistance, double yDistance, double oldXLeft,
			double oldYTop) {
		setXLeft(oldXLeft - xDistance);
		setYTop(oldYTop - yDistance);
	}

	public double pixelsToLogicalUnits(int pixels) {
		return (pixels / this.scalingFactor);
	}

	public void setScaling(double scaling) {
		setScalingFactor(scaling * this.minScalingFactor);
		setXCenter(this.xMin + 0.5D * (this.xMax - this.xMin));
		setYCenter(this.yMin + 0.5D * (this.yMax - this.yMin));
	}

	public void setScaling(double scaling, double xCenter, double yCenter) {
		setScalingFactor(scaling * this.minScalingFactor);
		setXCenter(xCenter);
		setYCenter(yCenter);
	}

	public void setScalingFactor(double scalingFactor) {
		this.scalingFactor = scalingFactor;
		this.scalingFactor = Math
				.max(this.scalingFactor, this.minScalingFactor);
		this.scalingFactor = Math.min(this.scalingFactor,
				MAX_SCALING * this.minScalingFactor);
		checkXLeft();
		checkYTop();
	}

	public void setXCenter(double xCenter) {
		this.xLeft = (xCenter - (0.5D * getWidth()));
		checkXLeft();
	}

	public void setXLeft(double xLeft) {
		this.xLeft = xLeft;
		checkXLeft();
	}

	public void setYCenter(double yCenter) {
		this.yTop = (yCenter - (0.5D * getHeight()));
		checkYTop();
	}

	public void setYTop(double yTop) {
		this.yTop = yTop;
		checkYTop();
	}
}