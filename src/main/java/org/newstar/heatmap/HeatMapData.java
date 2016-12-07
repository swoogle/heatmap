package org.newstar.heatmap;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Vector;

public class HeatMapData {
	private Vector<Item> items;
	private Vector<Item> itemsSorted;

	public HeatMapData() {
		this.items = new Vector<Item>();
		this.itemsSorted = new Vector<Item>();
	}

	public Item getItemSorted(int item) {
		return ((Item) this.itemsSorted.get(item));
	}

	public Item getItem(int item) {
		return ((Item) this.items.get(item));
	}

	public int getNItemsSorted() {
		return this.itemsSorted.size();
	}

	public int getNItems() {
		return this.items.size();
	}

	public void setItems(Vector<Item> items){
		this.items = items;
	}
	
	public double getMeanWeight(){
		double meanWeight = 0.0D;
		int nItems = this.getNItems();
		for (int i = 0; i < nItems; ++i){
			meanWeight += this.getItem(i).getWeight();
		}
		meanWeight /= nItems;
		return meanWeight;
	}

	public void normalizedItemWeight(){
		double meanWeight = getMeanWeight();
		int nItems = this.getNItems();
		for (int i = 0; i < nItems; ++i){
			Item item = this.getItem(i);
			double weight = item.getWeight();
			double normalizedWeight = weight / meanWeight;
			item.setNormalizedWeight(normalizedWeight);
		}
	}
	
	public void init(Graphics2D g2, ItemVisualization itemVisualization) {
		normalizedItemWeight();
		this.itemsSorted =  (Vector<Item>) this.items.clone();
		Collections.sort(this.itemsSorted, new ItemWeightComparator());
		for (int i = 0; i < getNItems(); ++i)
			((Item) this.items.get(i)).updateLabel(g2, itemVisualization);
		updateItemLabelScalingFactors(itemVisualization);
	}

	private void updateItemLabelScalingFactors(
			ItemVisualization itemVisualization) {
		for (int i = 0; i < this.itemsSorted.size(); ++i) {
			Item item1 = (Item) this.itemsSorted.get(i);
			item1.setLabelScalingFactor(4.9E-324D);
			for (int j = 0; j < i; ++j) {
				Item item2 = (Item) this.itemsSorted.get(j);
				double xLabelScalingFactor = 0.5D
						* (item1.getLabelFrameWidth()
								+ item2.getLabelFrameWidth()
								- (4 * itemVisualization
										.getLabelFrameBorderWidth()) + 2)
						/ Math.abs(item1.getX() - item2.getX());
				double yLabelScalingFactor = 0.5D
						* (item1.getLabelFrameHeight()
								+ item2.getLabelFrameHeight()
								- (4 * itemVisualization
										.getLabelFrameBorderHeight()) + 2)
						/ Math.abs(item1.getY() - item2.getY());
				double labelScalingFactor = Math.min(xLabelScalingFactor,
						yLabelScalingFactor);
				if ((labelScalingFactor > item1.getLabelScalingFactor())
						&& (labelScalingFactor > item2.getLabelScalingFactor()))
					item1.setLabelScalingFactor(labelScalingFactor);
			}
		}
	}
}