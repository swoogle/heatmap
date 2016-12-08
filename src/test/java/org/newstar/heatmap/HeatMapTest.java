package org.newstar.heatmap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.imageio.ImageIO;

public class HeatMapTest {
	public static final int HEATMAP_IMAGE_WIDTH = 800;
	public static final int HEATMAP_IMAGE_HEIGHT = 600;

	private static final HeatMap heatMap = new HeatMap(HEATMAP_IMAGE_WIDTH,
			HEATMAP_IMAGE_HEIGHT);

	private static InputStream getInputStream(final String path) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);
	}

	private static void writeToFile(BufferedImage bufferedImage,
			final String outputFileName) {
		String extension = "";
		final int i = outputFileName.lastIndexOf('.');
		if (i > 0) {
			extension = outputFileName.substring(i + 1);
		}
		try {
			ImageIO.write(bufferedImage, extension, new File(outputFileName));

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(
					getInputStream("data/test.json")));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString.trim());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		BufferedImage image = new BufferedImage(HEATMAP_IMAGE_WIDTH,
				HEATMAP_IMAGE_HEIGHT, 1);
		Graphics2D g2 = (Graphics2D) image.getGraphics();

		Vector<Item> items = heatMap.parseJSONData(sb.toString());
		if (items != null && items.size() > 0) {
			heatMap.getHeatMapData().setItems(items);
			heatMap.initItemCoordinates();
			heatMap.initMapParameters();
			heatMap.getHeatMapData().init(g2, heatMap.getItemVisualization());

			heatMap.calcDensities();
			heatMap.calcDensityColors();
			heatMap.drawDensity(g2);
			heatMap.drawLabels(g2, heatMap.getItemVisualization());

			writeToFile(image, "output/heatmap.png");
		}
	}
}
