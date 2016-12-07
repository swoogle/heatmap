package org.newstar.heatmap;

import java.util.Comparator;

public class ItemWeightComparator implements Comparator<Item> {
	public int compare(Item item1, Item item2) {
		return (int) Math.signum(item1.getNormalizedWeight()
				- item2.getNormalizedWeight());
	}
}