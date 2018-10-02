/**
 * 
 */
package com.sarvah.mbg.commons.communication;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shivu
 *
 */
public class VariantConstants {

	private static Map<String, String> variantMap = null;

	public static Map<String, String> getVarientConstantMap() {
		if (variantMap == null) {
			variantMap = new HashMap<String, String>();
			variantMap.put("TMT Steel", "mm");
			variantMap.put("Pipes & Fittings", "inch");
			variantMap.put("Water Heaters", "liter");
			variantMap.put("Pumps", "hp");
			variantMap.put("Wires & Cables", "mm");
			variantMap.put("CFL", "watts");
			variantMap.put("Bricks and Blocks", "mm");
			variantMap.put("Coarse Aggregate and Stones", "mm");
			variantMap.put("Cement", "grade");
		}
		return variantMap;
	}
}
