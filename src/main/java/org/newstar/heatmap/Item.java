package org.newstar.heatmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Item {
	private String id;
	private String labelMainText;
	private double x;
	private double y;
	private double normalizedWeight;
	private double weight;
	private ItemColors itemColors;
	private Font labelMainTextFont;
	private String labelMainTextSubstring;
	private int labelMainTextWidth;
	private int labelMainTextHeight;
	private int labelMainTextAscent;
	private double labelScalingFactor;
	private int labelAlpha;
	private int labelTextFrameWidth;
	private int labelTextFrameHeight;
	private int labelFrameWidth;
	private int labelFrameHeight;

	public Item(String id, String labelMainText, double x, double y,
			double weight) {
		this.id = id;
		this.labelMainText = labelMainText;
		this.x = x;
		this.y = y;
		this.weight = weight;
	}

	public String getId() {
		return this.id;
	}

	public Color getItemColor() {
		return ((this.itemColors != null) ? this.itemColors.getColor() : null);
	}

	public ItemColors getItemColors(ItemVisualization itemVisualization) {
		return itemVisualization.getDefaultLabelColors();
	}

	public int getLabelFrameHeight() {
		return this.labelFrameHeight;
	}

	public int getLabelFrameWidth() {
		return this.labelFrameWidth;
	}

	public String getLabelMainText() {
		return this.labelMainText;
	}

	public double getLabelScalingFactor() {
		return this.labelScalingFactor;
	}

	public int getLabelTextFrameHeight() {
		return this.labelTextFrameHeight;
	}

	public int getLabelTextFrameWidth() {
		return this.labelTextFrameWidth;
	}

	public double getNormalizedWeight() {
		return this.normalizedWeight;
	}

	public void setNormalizedWeight(double normalizedWeight) {
		this.normalizedWeight = normalizedWeight;
	}

	public double getWeight() {
		return this.weight;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x){
		this.x = x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setLabelScalingFactor(double labelScalingFactor) {
		this.labelScalingFactor = labelScalingFactor;
	}

	public void updateLabel(Graphics2D g2, ItemVisualization itemVisualization) {
		int labelMainTextFontSize = (int) (itemVisualization.getScale() * (itemVisualization
				.getLabelMainTextMinFontSize() + itemVisualization
				.getLabelMainTextFontSizeScalingConstant()
				* Math.pow(this.normalizedWeight,
						itemVisualization.getLabelSizeVariation())));
		this.labelMainTextFont = new Font(itemVisualization.getLabelFontName(),
				0, labelMainTextFontSize);
		this.labelMainTextSubstring = this.labelMainText.substring(
				0,
				Math.min(this.labelMainText.length(),
						itemVisualization.getLabelMainTextMaxLength()));
		g2.setFont(this.labelMainTextFont);
		FontMetrics labelFM = g2.getFontMetrics();
		this.labelMainTextWidth = labelFM
				.stringWidth(this.labelMainTextSubstring);
		this.labelMainTextHeight = (labelFM.getAscent() + labelFM.getDescent());
		this.labelMainTextAscent = labelFM.getAscent();
		this.labelTextFrameWidth = this.labelMainTextWidth;
		this.labelTextFrameHeight = this.labelMainTextHeight;
		this.labelFrameWidth = (this.labelMainTextWidth + 2 * (int) (itemVisualization
				.getScale() * itemVisualization.getLabelFrameBorderWidth()));
		this.labelFrameHeight = (this.labelMainTextHeight + 2 * (int) (itemVisualization
				.getScale() * itemVisualization.getLabelFrameBorderHeight()));
	}

	public void drawLabel(Graphics2D g2, int labelX, int labelY,
			double mapScalingFactor, ItemVisualization itemVisualization) {
		if ((!(itemVisualization.getNoOverlap()))
				|| (this.labelScalingFactor <= mapScalingFactor)) {
			this.labelAlpha = itemVisualization.getLabelMaxAlpha();
		} else if ((itemVisualization.getGradualAppearance())
				&& (itemVisualization.getLabelScalingConstant()
						* this.labelScalingFactor < mapScalingFactor)) {
			double alpha = (mapScalingFactor - (itemVisualization
					.getLabelScalingConstant() * this.labelScalingFactor))
					/ ((1.0D - itemVisualization.getLabelScalingConstant())
					* this.labelScalingFactor);
			this.labelAlpha = (int) (itemVisualization.getLabelMinAlpha()
					+ (itemVisualization.getLabelMaxAlpha() - itemVisualization
							.getLabelMinAlpha()) * alpha * alpha + 0.5D);
		} else if (itemVisualization.getBlurredBackground()) {
			this.labelAlpha = itemVisualization.getLabelMinAlpha();
		} else {
			this.labelAlpha = 0;
		}
		if ((this.labelAlpha < itemVisualization.getLabelMaxAlpha())
				&& (((itemVisualization.getNoTransparency()) || (this.labelAlpha <= 0))))
			return;
		g2.setColor(new Color(itemVisualization.getLabelColor().getRed(),
				itemVisualization.getLabelColor().getGreen(), itemVisualization
						.getLabelColor().getBlue(), this.labelAlpha));
		
		int labelMainTextX = labelX - ((this.labelMainTextWidth - 1) / 2);
		int labelMainTextY = labelY - ((this.labelMainTextHeight - 1) / 2)
				+ (int) (0.95D * this.labelMainTextAscent);
		g2.setFont(this.labelMainTextFont);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,  
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
		g2.drawString(this.labelMainTextSubstring, labelMainTextX,
				labelMainTextY);
	}
}