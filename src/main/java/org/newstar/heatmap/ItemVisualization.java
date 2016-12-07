package org.newstar.heatmap;

import java.awt.Color;

public class ItemVisualization implements Cloneable {
	public static final int USER_COLORS = 0;
	public static final int CLUSTER_COLORS = 1;
	public static final int SCORE_COLORS = 2;
	public static final int NO_COLORS = 3;
	public static final int LABELS_AND_CIRCLES = 0;
	public static final int LABELS_AND_FRAMES = 1;
	public static final int LABELS_AND_DOTS = 2;
	public static final int LABELS_ONLY = 3;
	public static final ItemColors DEFAULT_LABEL_COLORS = new ItemColors(
			new Color(170, 170, 170));
	public static final String LABEL_FONT_NAME = "宋体";
	public static final int LABEL_MAIN_TEXT_MIN_FONT_SIZE = 12;
	public static final int LABEL_MAIN_TEXT_FONT_SIZE_SCALING_CONSTANT = 4;
	public static final int LABEL_SUB_TEXT_FONT_SIZE = 8;
	public static final int LABEL_SUB_TEXT_MIN_LENGTH = 15;
	public static final double LABEL_MAIN_SUB_TEXT_REL_LENGTH = 1.3D;
	public static final Color LABEL_COLOR = Color.BLACK;
	public static final double LABEL_SCALING_CONSTANT = 0.6D;
	public static final int LABEL_MIN_ALPHA = 10;
	public static final int LABEL_MAX_ALPHA = 230;
	public static final int LABEL_MIN_ALPHA_MOUSE_OVER = 10;
	public static final int LABEL_FRAME_BORDER_WIDTH = 10;
	public static final int LABEL_FRAME_BORDER_HEIGHT = 2;
	public static final int LABEL_FRAME_ARC_SIZE = 12;
	public static final boolean CIRCLE_FIXED_SIZE = false;
	public static final int CIRCLE_MIN_DIAMETER = 5;
	public static final int CIRCLE_AVERAGE_DIAMETER = 16;
	public static final int CIRCLE_DIAMETER_SCATTER_VIEW = 5;
	public static final int CIRCLE_EXTRA_DIAMETER_SELECTED = 2;
	public static final boolean CIRCLE_BORDER = false;
	public static final double SCALE = 1.0D;
	public static final double LABEL_SIZE_VARIATION = 0.5D;
	public static final int LABEL_MAIN_TEXT_MAX_LENGTH = 30;
	public static final boolean NO_OVERLAP = true;
	public static final boolean BLURRED_BACKGROUND = false;
	public static final boolean GRADUAL_APPEARANCE = true;
	public static final int LABEL_VIEW_VISUALIZATION = 0;
	public static final int ITEM_COLORS = 3;
	public static final int ITEM_WEIGHT = 0;
	public static final boolean USE_BLACK_BACKGROUND_LABEL_VIEW = false;
	public static final boolean NO_TRANSPARENTCY = false;
	public static final boolean NO_GRADIENT = false;
	private ItemColors defaultLabelColors;
	private String labelFontName;
	private int labelMainTextMinFontSize;
	private int labelMainTextFontSizeScalingConstant;
	private int labelMainTextMaxLength;
	private Color labelColor;
	private double labelScalingConstant;
	private int labelMinAlpha;
	private int labelMaxAlpha;
	private int labelMinAlphaMouseOver;
	private int labelFrameBorderWidth;
	private int labelFrameBorderHeight;
	private int labelFrameArcSize;
	private boolean noOverlap;
	private boolean blurredBackground;
	private boolean gradualAppearance;
	private int labelViewVisualization;
	private int itemColors;
	private int itemWeight;
	private boolean useBlackBackgroundLabelView;
	private double scale;
	private double labelSizeVariation;
	private boolean noTransparency;
	private boolean noGradient;

	public ItemVisualization() {
		this.defaultLabelColors = DEFAULT_LABEL_COLORS;
		this.labelFontName = LABEL_FONT_NAME;
		this.labelMainTextMinFontSize = LABEL_MAIN_TEXT_MIN_FONT_SIZE;
		this.labelMainTextFontSizeScalingConstant = LABEL_MAIN_TEXT_FONT_SIZE_SCALING_CONSTANT;
		this.labelMainTextMaxLength = LABEL_MAIN_TEXT_MAX_LENGTH;
		this.labelColor = LABEL_COLOR;
		this.labelScalingConstant = LABEL_SCALING_CONSTANT;
		this.labelMinAlpha = LABEL_MIN_ALPHA;
		this.labelMaxAlpha = LABEL_MAX_ALPHA;
		this.labelMinAlphaMouseOver = LABEL_MIN_ALPHA_MOUSE_OVER;
		this.labelFrameBorderWidth = LABEL_FRAME_BORDER_WIDTH;
		this.labelFrameBorderHeight = LABEL_FRAME_BORDER_HEIGHT;
		this.labelFrameArcSize = LABEL_FRAME_ARC_SIZE;

		this.noOverlap = NO_OVERLAP;
		this.blurredBackground = BLURRED_BACKGROUND;
		this.gradualAppearance = GRADUAL_APPEARANCE;
		this.labelViewVisualization = LABEL_VIEW_VISUALIZATION;
		this.itemColors = ITEM_COLORS;
		this.itemWeight = ITEM_WEIGHT;
		this.useBlackBackgroundLabelView = USE_BLACK_BACKGROUND_LABEL_VIEW;

		this.scale = SCALE;
		this.labelSizeVariation = LABEL_SIZE_VARIATION;

		this.noTransparency = NO_TRANSPARENTCY;
		this.noGradient = NO_GRADIENT;
	}

	public ItemVisualization clone() {
		ItemVisualization clone;
		try {
			clone = (ItemVisualization) super.clone();
		} catch (CloneNotSupportedException localCloneNotSupportedException) {
			return null;
		}
		return clone;
	}

	public boolean getBlurredBackground() {
		return this.blurredBackground;
	}

	public ItemColors getDefaultLabelColors() {
		return this.defaultLabelColors;
	}

	public boolean getGradualAppearance() {
		return this.gradualAppearance;
	}

	public int getItemColors() {
		return this.itemColors;
	}

	public int getItemWeight() {
		return this.itemWeight;
	}

	public Color getLabelColor() {
		return this.labelColor;
	}

	public String getLabelFontName() {
		return this.labelFontName;
	}

	public int getLabelFrameArcSize() {
		return this.labelFrameArcSize;
	}

	public int getLabelFrameBorderHeight() {
		return this.labelFrameBorderHeight;
	}

	public int getLabelFrameBorderWidth() {
		return this.labelFrameBorderWidth;
	}

	public int getLabelMainTextFontSizeScalingConstant() {
		return this.labelMainTextFontSizeScalingConstant;
	}

	public int getLabelMainTextMaxLength() {
		return this.labelMainTextMaxLength;
	}

	public int getLabelMainTextMinFontSize() {
		return this.labelMainTextMinFontSize;
	}

	public int getLabelMaxAlpha() {
		return this.labelMaxAlpha;
	}

	public int getLabelMinAlpha() {
		return this.labelMinAlpha;
	}

	public int getLabelMinAlphaMouseOver() {
		return this.labelMinAlphaMouseOver;
	}

	public double getLabelScalingConstant() {
		return this.labelScalingConstant;
	}

	public double getLabelSizeVariation() {
		return this.labelSizeVariation;
	}

	public int getLabelViewVisualization() {
		return this.labelViewVisualization;
	}

	public boolean getNoGradient() {
		return this.noGradient;
	}

	public boolean getNoOverlap() {
		return this.noOverlap;
	}

	public boolean getNoTransparency() {
		return this.noTransparency;
	}

	public double getScale() {
		return this.scale;
	}

	public boolean getUseBlackBackgroundLabelView() {
		return this.useBlackBackgroundLabelView;
	}

	public void setBlurredBackground(boolean blurredBackground) {
		this.blurredBackground = blurredBackground;
	}

	public void setDefaultLabelColors(ItemColors defaultLabelColors) {
		this.defaultLabelColors = defaultLabelColors;
	}

	public void setGradualAppearance(boolean gradualAppearance) {
		this.gradualAppearance = gradualAppearance;
	}

	public void setItemColors(int itemColors) {
		this.itemColors = itemColors;
	}

	public void setItemWeight(int itemWeight) {
		this.itemWeight = itemWeight;
	}

	public void setLabelColor(Color labelColor) {
		this.labelColor = labelColor;
	}

	public void setLabelFontName(String labelFontName) {
		this.labelFontName = labelFontName;
	}

	public void setLabelFrameArcSize(int labelFrameArcSize) {
		this.labelFrameArcSize = labelFrameArcSize;
	}

	public void setLabelFrameBorderHeight(int labelFrameBorderHeight) {
		this.labelFrameBorderHeight = labelFrameBorderHeight;
	}

	public void setLabelFrameBorderWidth(int labelFrameBorderWidth) {
		this.labelFrameBorderWidth = labelFrameBorderWidth;
	}

	public void setLabelMainTextFontSizeScalingConstant(
			int labelMainTextFontSizeScalingConstant) {
		this.labelMainTextFontSizeScalingConstant = labelMainTextFontSizeScalingConstant;
	}

	public void setLabelMainTextMaxLength(int labelMainTextMaxLength) {
		this.labelMainTextMaxLength = labelMainTextMaxLength;
	}

	public void setLabelMainTextMinFontSize(int labelMainTextMinFontSize) {
		this.labelMainTextMinFontSize = labelMainTextMinFontSize;
	}

	public void setLabelMaxAlpha(int labelMaxAlpha) {
		this.labelMaxAlpha = labelMaxAlpha;
	}

	public void setLabelMinAlpha(int labelMinAlpha) {
		this.labelMinAlpha = labelMinAlpha;
	}

	public void setLabelMinAlphaMouseOver(int labelMinAlphaMouseOver) {
		this.labelMinAlphaMouseOver = labelMinAlphaMouseOver;
	}

	public void setLabelScalingConstant(double labelScalingConstant) {
		this.labelScalingConstant = labelScalingConstant;
	}

	public void setLabelSizeVariation(double labelSizeVariation) {
		this.labelSizeVariation = labelSizeVariation;
	}

	public void setLabelViewVisualization(int labelViewVisualization) {
		this.labelViewVisualization = labelViewVisualization;
	}

	public void setNoGradient(boolean noGradient) {
		this.noGradient = noGradient;
	}

	public void setNoOverlap(boolean noOverlap) {
		this.noOverlap = noOverlap;
	}

	public void setNoTransparency(boolean noTransparency) {
		this.noTransparency = noTransparency;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void setUseBlackBackgroundLabelView(
			boolean useBlackBackgroundLabelView) {
		this.useBlackBackgroundLabelView = useBlackBackgroundLabelView;
	}
}