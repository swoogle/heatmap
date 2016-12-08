package org.newstar.heatmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HeatMap {
	public static final int TYPE_COORDINATES_RANDOM = 0;
	public static final int TYPE_COORDINATES_CIRCLE = 1;
	public static final int TYPE_COORDINATES_ELLPISE = 2;

	private int heatMapImageWidth;
	private int heatMapImageHeight;
	private double circleRadius;
	private double ellipseAAxis;
	private double ellipseBAxis;
	private ItemVisualization itemVisualization;
	private DensityVisualization densityVisualization;
	private DensityColors densityColors;
	private HeatMapData heatmapData;
	private HeatMapParameters heatmapParameters;
	private double[][] coordinate;
	private double[][] densitiesAllItems;
	private double densityAllItemsMax;
	private BufferedImage densityImage;

	public HeatMap(int heatMapImageWidth, int heatMapImageHeight) {
		this.heatMapImageWidth = heatMapImageWidth;
		this.heatMapImageHeight = heatMapImageHeight;
		this.circleRadius = (double) this.heatMapImageHeight
				/ (double) this.heatMapImageWidth;
		this.ellipseAAxis = (double) this.heatMapImageWidth / 1000D;
		this.ellipseBAxis = (double) this.heatMapImageHeight / 1000D;
		this.itemVisualization = new ItemVisualization();
		this.itemVisualization.setItemWeight(0);

		this.densityVisualization = new DensityVisualization();
		this.densityColors = new DensityColors();
		this.heatmapData = new HeatMapData();
		this.heatmapParameters = new HeatMapParameters();
	}

	public HeatMapData getHeatMapData() {
		return this.heatmapData;
	}

	public ItemVisualization getItemVisualization() {
		return this.itemVisualization;
	}

	public void initItemCoordinates() {
		int nItems = this.heatmapData.getNItems();
		this.coordinate = new double[2][nItems];

		for (int i = 0; i < nItems; ++i) {
			this.coordinate[0][i] = this.heatmapData.getItem(i).getX();
			this.coordinate[1][i] = this.heatmapData.getItem(i).getY();
		}

		// standardizeCoordinates(true);
		// for (int i = 0; i < nItems; ++i) {
		// this.heatmapData.getItem(i).setX(this.coordinate[0][i]);
		// this.heatmapData.getItem(i).setY(this.coordinate[1][i]);
		// }
	}

	public void initMapParameters() {
		this.heatmapParameters.init(this.getMinCoordinates(),
				this.getMaxCoordinates(), this.heatMapImageWidth,
				this.heatMapImageHeight, 0, 0);
	}

	private double[] getMaxCoordinates() {
		double[] maxCoordinate = new double[2];
		maxCoordinate[0] = Arrays2.calcMaximum(this.coordinate[0]);
		maxCoordinate[1] = Arrays2.calcMaximum(this.coordinate[1]);
		return maxCoordinate;
	}

	private double[] getMinCoordinates() {
		double[] minCoordinate = new double[2];
		minCoordinate[0] = Arrays2.calcMinimum(this.coordinate[0]);
		minCoordinate[1] = Arrays2.calcMinimum(this.coordinate[1]);
		return minCoordinate;
	}

	private void standardizeCoordinates(boolean standardizeDistances) {
		int nItems = this.heatmapData.getNItems();
		double averageCoordinate1 = Arrays2.calcAverage(this.coordinate[0]);
		double averageCoordinate2 = Arrays2.calcAverage(this.coordinate[1]);
		for (int i = 0; i < nItems; ++i) {
			this.coordinate[0][i] -= averageCoordinate1;
			this.coordinate[1][i] -= averageCoordinate2;
		}

		double variance1 = 0.0D;
		double variance2 = 0.0D;
		double covariance = 0.0D;
		for (int i = 0; i < nItems; ++i) {
			variance1 += this.coordinate[0][i] * this.coordinate[0][i];
			variance2 += this.coordinate[1][i] * this.coordinate[1][i];
			covariance += this.coordinate[0][i] * this.coordinate[1][i];
		}
		variance1 /= nItems;
		variance2 /= nItems;
		covariance /= nItems;
		double discriminant = variance1 * variance1 + variance2 * variance2
				- (2.0D * variance1 * variance2) + 4.0D * covariance
				* covariance;
		double eigenvalue1 = (variance1 + variance2 - Math.sqrt(discriminant)) / 2.0D;
		double eigenvalue2 = (variance1 + variance2 + Math.sqrt(discriminant)) / 2.0D;
		double normalizedEigenvector11 = variance1 + covariance - eigenvalue1;
		double normalizedEigenvector12 = variance2 + covariance - eigenvalue1;
		double vectorLength = Math.sqrt(normalizedEigenvector11
				* normalizedEigenvector11 + normalizedEigenvector12
				* normalizedEigenvector12);
		normalizedEigenvector11 /= vectorLength;
		normalizedEigenvector12 /= vectorLength;
		double normalizedEigenvector21 = variance1 + covariance - eigenvalue2;
		double normalizedEigenvector22 = variance2 + covariance - eigenvalue2;
		vectorLength = Math.sqrt(normalizedEigenvector21
				* normalizedEigenvector21 + normalizedEigenvector22
				* normalizedEigenvector22);
		normalizedEigenvector21 /= vectorLength;
		normalizedEigenvector22 /= vectorLength;
		for (int i = 0; i < nItems; ++i) {
			double coordinateOld1 = this.coordinate[0][i];
			double coordinateOld2 = this.coordinate[1][i];
			this.coordinate[0][i] = (normalizedEigenvector11 * coordinateOld1 + normalizedEigenvector12
					* coordinateOld2);
			this.coordinate[1][i] = (normalizedEigenvector21 * coordinateOld1 + normalizedEigenvector22
					* coordinateOld2);
		}

		for (int i = 0; i < 2; ++i) {
			if (Arrays2.calcMedian(this.coordinate[i]) > 0.0D)
				for (int j = 0; j < nItems; ++j)
					this.coordinate[i][j] *= -1.0D;
		}
		if (!(standardizeDistances))
			return;
		double averageDistance = getAverageDistance();
		for (int i = 0; i < nItems; ++i) {
			this.coordinate[0][i] /= averageDistance;
			this.coordinate[1][i] /= averageDistance;
		}
	}

	private double[][] calcCoordinates(double x, double y, int number,
			double radius, double aAxis, double bAxis, int type) {
		double[][] coordinates = new double[2][number];
		if (type == TYPE_COORDINATES_RANDOM) {
			calcRandomCoordinates(x, y, number);
		} else if (type == TYPE_COORDINATES_CIRCLE) {
			coordinates = calcCircleCoordinates(x, y, number, radius);
		} else if (type == TYPE_COORDINATES_ELLPISE) {
			coordinates = calcEllipseCoordinates(x, y, number, aAxis, bAxis);
		}
		return coordinates;
	}

	private double[][] calcRandomCoordinates(double x, double y, int number) {
		double[][] coordinates = new double[2][number];
		Random random = new Random();
		for (int i = 0; i < number; i++) {
			coordinates[0][i] = x + random.nextDouble();
			coordinates[1][i] = y + random.nextDouble();
		}
		return coordinates;
	}

	private double[][] calcCircleCoordinates(double x, double y, int number,
			double radius) {
		double[][] coordinates = new double[2][number];
		for (int i = 0; i < number; i++) {
			coordinates[0][i] = x + radius
					* Math.cos((double) i / number * 2 * Math.PI);
			coordinates[1][i] = y + radius
					* Math.sin((double) i / number * 2 * Math.PI);
		}
		return coordinates;
	}

	private double[][] calcEllipseCoordinates(double x, double y, int number,
			double aAxis, double bAxis) {
		double[][] coordinates = new double[2][number];
		for (int i = 0; i < number; i++) {
			coordinates[0][i] = x + aAxis
					* Math.cos((double) i / number * 2 * Math.PI);
			coordinates[1][i] = y + bAxis
					* Math.sin((double) i / number * 2 * Math.PI);
		}
		return coordinates;
	}

	private double getAverageDistance() {
		double averageDistance = 0.0D;
		int nItems = this.heatmapData.getNItems();
		for (int i = 0; i < nItems; ++i)
			for (int j = 0; j < i; ++j) {
				double distance1 = this.heatmapData.getItem(i).getX()
						- this.heatmapData.getItem(j).getX();
				double distance2 = this.heatmapData.getItem(i).getY()
						- this.heatmapData.getItem(j).getY();
				averageDistance += Math.sqrt(distance1 * distance1 + distance2
						* distance2);
			}
		averageDistance /= nItems * (nItems - 1) / 2;
		return averageDistance;
	}

	public void calcDensities() {
		double kernelWidth = this.densityVisualization.getKernelWidth()
				* getAverageDistance();
		double squaredKernelWidth = kernelWidth * kernelWidth;

		double[] x = new double[this.densityVisualization.getGridSize()];
		double[] y = new double[this.densityVisualization.getGridSize()];
		double xStep = (this.heatmapParameters.getXMax() - this.heatmapParameters
				.getXMin()) / (this.densityVisualization.getGridSize() - 1);
		double yStep = (this.heatmapParameters.getYMax() - this.heatmapParameters
				.getYMin()) / (this.densityVisualization.getGridSize() - 1);
		x[0] = this.heatmapParameters.getXMin();
		y[0] = this.heatmapParameters.getYMin();
		for (int i = 1; i < this.densityVisualization.getGridSize(); i++) {
			x[i] = (x[(i - 1)] + xStep);
			y[i] = (y[(i - 1)] + yStep);
		}

		this.densitiesAllItems = new double[this.densityVisualization
				.getGridSize()][this.densityVisualization.getGridSize()];
		int nItems = this.heatmapData.getNItems();
		for (int l = 0; l < nItems; l++) {
			Item item = this.heatmapData.getItem(l);
			double logItemDensityThreshold = Math.log(this.densityVisualization
					.getItemDensityThreshold() / item.getNormalizedWeight());
			double thresholdDist = Math.sqrt(-logItemDensityThreshold
					* squaredKernelWidth);
			int i1 = Math
					.max((int) ((item.getX() - thresholdDist - this.heatmapParameters
							.getXMin()) / xStep + 1.0D), 0);
			int i2 = Math
					.min((int) ((item.getX() + thresholdDist - this.heatmapParameters
							.getXMin()) / xStep), this.densityVisualization
							.getGridSize() - 1);
			int j1 = Math
					.max((int) ((item.getY() - thresholdDist - this.heatmapParameters
							.getYMin()) / yStep + 1.0D), 0);
			int j2 = Math
					.min((int) ((item.getY() + thresholdDist - this.heatmapParameters
							.getYMin()) / yStep), this.densityVisualization
							.getGridSize() - 1);
			for (int i = i1; i <= i2; i++) {
				for (int j = j1; j <= j2; j++) {
					double xDist = x[i] - item.getX();
					double yDist = y[j] - item.getY();
					double logItemDensity = -(xDist * xDist + yDist * yDist)
							/ squaredKernelWidth;
					if (logItemDensity >= logItemDensityThreshold) {
						double itemDensity = item.getNormalizedWeight()
								* Math.exp(logItemDensity);
						this.densitiesAllItems[i][j] += itemDensity;
					}
				}
			}
		}
		this.densityAllItemsMax = 4.9E-324D;
		for (int i = 0; i < this.densityVisualization.getGridSize(); i++)
			for (int j = 0; j < this.densityVisualization.getGridSize(); j++) {
				this.densityAllItemsMax = Math.max(this.densityAllItemsMax,
						this.densitiesAllItems[i][j]);
			}
	}

	public void calcDensityColors() {
		double colorTransformation = this.densityVisualization
				.getColorTransformation();
		int[] densityColors = new int[this.densityVisualization.getGridSize()
				* this.densityVisualization.getGridSize()];
		for (int i = 0; i < this.densityVisualization.getGridSize(); i++)
			for (int j = 0; j < this.densityVisualization.getGridSize(); j++) {
				double colorStrength = 1.0D - Math
						.pow(1.0D - Math.pow(this.densitiesAllItems[i][j]
								/ this.densityAllItemsMax, colorTransformation),
								1.0D / colorTransformation);

				Color densityColor = this.densityColors.getColor(colorStrength);
				int red = densityColor.getRed();
				int green = densityColor.getGreen();
				int blue = densityColor.getBlue();
				densityColors[(j * this.densityVisualization.getGridSize() + i)] = (red << 16
						| green << 8 | blue);
			}
		BufferedImage densityGridImage = new BufferedImage(
				this.densityVisualization.getGridSize(),
				this.densityVisualization.getGridSize(), 1);
		densityGridImage.setRGB(0, 0, this.densityVisualization.getGridSize(),
				this.densityVisualization.getGridSize(), densityColors, 0,
				this.densityVisualization.getGridSize());
		int gridSize2 = 1000;
		this.densityImage = new BufferedImage(gridSize2, gridSize2, 1);
		Graphics2D g2Image = (Graphics2D) this.densityImage.getGraphics();
		g2Image.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2Image.drawImage(densityGridImage, 0, 0, gridSize2, gridSize2, null);
		g2Image.dispose();
	}

	public void drawDensity(Graphics2D g2) {
		int x = this.heatmapParameters
				.logicalUnitsToPixels(this.heatmapParameters.getXMin()
						- this.heatmapParameters.getXLeft());
		int y = this.heatmapParameters
				.logicalUnitsToPixels(this.heatmapParameters.getYMin()
						- this.heatmapParameters.getYTop());
		int width = this.heatmapParameters
				.logicalUnitsToPixels(this.heatmapParameters.getXMax()
						- this.heatmapParameters.getXMin());
		int height = this.heatmapParameters
				.logicalUnitsToPixels(this.heatmapParameters.getYMax()
						- this.heatmapParameters.getYMin());
		BufferedImage mapImage = new BufferedImage(this.heatMapImageWidth,
				this.heatMapImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2Image = (Graphics2D) mapImage.getGraphics();
		g2Image.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2Image.drawImage(this.densityImage, x, y, width, height, null);
		g2Image.dispose();
		g2.drawImage(mapImage, 0, 0, null);
	}

	public void drawLabels(Graphics2D g2, ItemVisualization itemVisualization) {
		for (int i = this.heatmapData.getNItems() - 1; i >= 0; i--) {
			Item item = this.heatmapData.getItemSorted(i);
			int x = this.heatmapParameters.logicalUnitsToPixels(item.getX()
					- this.heatmapParameters.getXLeft());
			int y = this.heatmapParameters.logicalUnitsToPixels(item.getY()
					- this.heatmapParameters.getYTop());
			item.drawLabel(g2, x, y, this.heatmapParameters.getScalingFactor(),
					itemVisualization);
		}
	}

	public Vector<Item> parseJSONData(String jsonData) {
		Vector<Item> items = new Vector<Item>();

		JSONObject retObj = JSON.parseObject(jsonData.toString());
		boolean ok = retObj.getBoolean("msg");
		if (ok) {
			String mkey = retObj.getString("mkey");
			JSONObject mkeyObj = JSON.parseObject(mkey);
			String centerKeywords = mkeyObj.getString("keywords");
			double centerWeight = mkeyObj.getDoubleValue("frequency");
			JSONArray centerArray = mkeyObj.getJSONArray("cont");
			int id = 0;

			// Item center = new Item(Integer.toString(id), centerKeywords, 0,
			// 0,
			// centerWeight);
			// items.add(center);
			if (centerArray != null && centerArray.size() > 0) {
				int number = centerArray.size();
				double[][] firstLayerCoordinates = calcCoordinates(0, 0,
						number, this.circleRadius, this.ellipseAAxis,
						this.ellipseBAxis, TYPE_COORDINATES_ELLPISE);

				for (int i = 0; i < number; i++) {
					JSONObject firstLayerObj = (JSONObject) centerArray.get(i);
					String firstLayerKeywords = firstLayerObj
							.getString("keywords");
					double firstLayerWeight = firstLayerObj
							.getDoubleValue("frequency");

					Item firstLayerItem = new Item(Integer.toString(id++),
							firstLayerKeywords, firstLayerCoordinates[0][i],
							firstLayerCoordinates[1][i], firstLayerWeight);
					items.add(firstLayerItem);

					JSONArray firstLayerArray = firstLayerObj
							.getJSONArray("cont");
					if (firstLayerArray != null && firstLayerArray.size() > 0) {
						int firstLayerNumber = firstLayerArray.size();

						double[][] secondLayerCoordinates = calcCoordinates(
								firstLayerCoordinates[0][i],
								firstLayerCoordinates[1][i], firstLayerNumber,
								this.circleRadius / 4, this.ellipseAAxis / 4,
								this.ellipseBAxis / 4, TYPE_COORDINATES_ELLPISE);

						for (int j = 0; j < firstLayerNumber; j++) {
							JSONObject sencondLayerObj = (JSONObject) firstLayerArray
									.get(j);
							String sencondLayerKeywords = sencondLayerObj
									.getString("keywords");
							double sencondLayerWeight = sencondLayerObj
									.getDoubleValue("frequency");

							Item sencondLayerItem = new Item(
									Integer.toString(id++),
									sencondLayerKeywords,
									secondLayerCoordinates[0][j],
									secondLayerCoordinates[1][j],
									sencondLayerWeight);
							items.add(sencondLayerItem);
						}
					}
				}
			}
		}
		return items;
	}
}
