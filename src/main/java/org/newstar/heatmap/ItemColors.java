package org.newstar.heatmap;

import java.awt.Color;

public class ItemColors {
	public static final int CIRCLE_ALPHA = 180;
	public static final int CIRCLE_SELECTED_ALPHA = 255;
	public static final int LINE_ALPHA = 120;
	public static final int LINE_ALPHA_SELECTED = 200;
	private Color color;
	private Color labelLightColor;
	private Color labelDarkColor;
	private Color labelBorderColor;
	private Color circleColor;
	private Color circleLightColor;
	private Color circleDarkColor;
	private Color circleSelectedColor;
	private Color circleSelectedLightColor;
	private Color circleSelectedDarkColor;
	private Color linkColor;
	private Color linkColorSelected;

	public ItemColors(Color color) {
		this.color = color;
		this.labelLightColor = calcLightColor(color, 0.7D, 255);
		this.labelDarkColor = color;
		this.labelBorderColor = calcDarkColor(color, 0.5D, 255);
		this.circleColor = new Color(color.getRed(), color.getGreen(),
				color.getBlue(), 180);
		this.circleLightColor = calcLightColor(color, 0.6D, 180);
		this.circleDarkColor = calcDarkColor(color, 0.1D, 180);
		this.circleSelectedColor = new Color(this.circleColor.getRed(),
				this.circleColor.getGreen(), this.circleColor.getBlue(), 255);
		this.circleSelectedLightColor = new Color(
				this.circleLightColor.getRed(),
				this.circleLightColor.getGreen(),
				this.circleLightColor.getBlue(), 255);
		this.circleSelectedDarkColor = new Color(this.circleDarkColor.getRed(),
				this.circleDarkColor.getGreen(),
				this.circleDarkColor.getBlue(), 255);
		this.linkColor = new Color(this.circleLightColor.getRed(),
				this.circleLightColor.getGreen(),
				this.circleLightColor.getBlue(), 120);
		this.linkColorSelected = new Color(this.circleLightColor.getRed(),
				this.circleLightColor.getGreen(),
				this.circleLightColor.getBlue(), 200);
	}

	private Color calcDarkColor(Color color, double transitionStrength,
			int alpha) {
		int red = (int) ((1.0D - transitionStrength) * color.getRed());
		int green = (int) ((1.0D - transitionStrength) * color.getGreen());
		int blue = (int) ((1.0D - transitionStrength) * color.getBlue());
		return new Color(red, green, blue, alpha);
	}

	private Color calcLightColor(Color color, double transitionStrength,
			int alpha) {
		int red = (int) ((1.0D - transitionStrength) * color.getRed() + transitionStrength * 255.0D);
		int green = (int) ((1.0D - transitionStrength) * color.getGreen() + transitionStrength * 255.0D);
		int blue = (int) ((1.0D - transitionStrength) * color.getBlue() + transitionStrength * 255.0D);
		return new Color(red, green, blue, alpha);
	}

	public Color getCircleColor() {
		return this.circleColor;
	}

	public Color getCircleDarkColor() {
		return this.circleDarkColor;
	}

	public Color getCircleLightColor() {
		return this.circleLightColor;
	}

	public Color getCircleSelectedColor() {
		return this.circleSelectedColor;
	}

	public Color getCircleSelectedDarkColor() {
		return this.circleSelectedDarkColor;
	}

	public Color getCircleSelectedLightColor() {
		return this.circleSelectedLightColor;
	}

	public Color getColor() {
		return this.color;
	}

	public Color getLabelBorderColor() {
		return this.labelBorderColor;
	}

	public Color getLabelDarkColor() {
		return this.labelDarkColor;
	}

	public Color getLabelLightColor() {
		return this.labelLightColor;
	}

	public Color getLinkColor() {
		return this.linkColor;
	}

	public Color getLinkColorSelected() {
		return this.linkColorSelected;
	}
}