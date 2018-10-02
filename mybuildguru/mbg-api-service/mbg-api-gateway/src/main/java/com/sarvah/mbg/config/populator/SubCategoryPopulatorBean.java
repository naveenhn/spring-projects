package com.sarvah.mbg.config.populator;

public class SubCategoryPopulatorBean {
	// SubCategoryDAO subCategoryDAO;
	//
	// CategoryDAO categoryDAO;
	//
	// public SubCategoryPopulatorBean(SubCategoryDAO subCategoryDAO,
	// CategoryDAO categoryDAO) {
	// this.subCategoryDAO = subCategoryDAO;
	// this.categoryDAO = categoryDAO;
	// }
	//
	// public void initSubCategories() {
	//
	// subCategoryDAO.deleteAll();
	// List<Category> categories = categoryDAO
	// .findByNameAllIgnoreCase("Plumbing");
	//
	// SubCategory plumbingSubcat1 = new SubCategory();
	//
	// if (categories != null && categories.size() != 0) {
	//
	// plumbingSubcat1.setCategory(categories.get(0));
	// Description desc = new Description("en", "Tubs");
	// plumbingSubcat1.setDesc(desc);
	// plumbingSubcat1.setName("Tubs");
	// subCategoryDAO.insert(plumbingSubcat1);
	//
	// SubCategory plumbingSubcat2 = new SubCategory();
	// plumbingSubcat2.setCategory(categories.get(0));
	// Description desc1 = new Description("en", "Wash Basins");
	// plumbingSubcat2.setDesc(desc1);
	// plumbingSubcat2.setName("Wash Basins");
	//
	// subCategoryDAO.insert(plumbingSubcat2);
	//
	// SubCategory plumbingSubcat3 = new SubCategory();
	// plumbingSubcat3.setCategory(categories.get(0));
	// Description desc2 = new Description("en", "WC");
	// plumbingSubcat3.setDesc(desc2);
	// plumbingSubcat3.setName("WC");
	//
	// subCategoryDAO.insert(plumbingSubcat3);
	//
	// SubCategory plumbingSubcat4 = new SubCategory();
	// plumbingSubcat4.setCategory(categories.get(0));
	// Description desc3 = new Description("en",
	// "Shower Panels  & Systems");
	// plumbingSubcat4.setDesc(desc3);
	// plumbingSubcat4.setName("Shower Panels  & Systems");
	//
	// subCategoryDAO.insert(plumbingSubcat4);
	//
	// SubCategory plumbingSubcat5 = new SubCategory();
	// plumbingSubcat5.setCategory(categories.get(0));
	// Description desc4 = new Description("en", "Faucets/Taps");
	// plumbingSubcat5.setDesc(desc4);
	// plumbingSubcat5.setName("Faucets/Taps");
	//
	// subCategoryDAO.insert(plumbingSubcat5);
	//
	// SubCategory plumbingSubcat6 = new SubCategory();
	// plumbingSubcat6.setCategory(categories.get(0));
	// Description desc5 = new Description("en", "Pipes & Fittings");
	// plumbingSubcat6.setDesc(desc5);
	// plumbingSubcat6.setName("Pipes & Fittings");
	//
	// subCategoryDAO.insert(plumbingSubcat6);
	//
	// SubCategory plumbingSubcat7 = new SubCategory();
	// plumbingSubcat7.setCategory(categories.get(0));
	// Description desc6 = new Description("en", "Water Heaters");
	// plumbingSubcat7.setDesc(desc6);
	// plumbingSubcat7.setName("Water Heaters");
	//
	// subCategoryDAO.insert(plumbingSubcat7);
	//
	// SubCategory plumbingSubcat8 = new SubCategory();
	// plumbingSubcat8.setCategory(categories.get(0));
	// Description desc7 = new Description("en", "Pumps");
	// plumbingSubcat8.setDesc(desc7);
	// plumbingSubcat8.setName("Pumps");
	//
	// subCategoryDAO.insert(plumbingSubcat8);
	//
	// SubCategory plumbingSubcat9 = new SubCategory();
	// plumbingSubcat9.setCategory(categories.get(0));
	// Description desc8 = new Description("en", "Accessories");
	// plumbingSubcat9.setDesc(desc8);
	// plumbingSubcat9.setName("Accessories");
	//
	// subCategoryDAO.insert(plumbingSubcat9);
	// }
	// List<Category> categories1 = categoryDAO
	// .findByNameAllIgnoreCase("Electricals");
	//
	// SubCategory electricalSubcat = new SubCategory();
	//
	// if (categories1 != null && categories1.size() != 0) {
	//
	// electricalSubcat.setCategory(categories1.get(0));
	// Description desc = new Description("en", "Pipes & Fittings");
	// electricalSubcat.setDesc(desc);
	// electricalSubcat.setName("Pipes & Fittings");
	//
	// subCategoryDAO.insert(electricalSubcat);
	//
	// SubCategory electricalSubcat1 = new SubCategory();
	// electricalSubcat1.setCategory(categories1.get(0));
	// Description desc1 = new Description("en", "Wires & Cables");
	// electricalSubcat1.setDesc(desc1);
	// electricalSubcat1.setName("Wires & Cables");
	//
	// subCategoryDAO.insert(electricalSubcat1);
	//
	// SubCategory electricalSubcat2 = new SubCategory();
	// electricalSubcat2.setCategory(categories1.get(0));
	// Description desc2 = new Description("en", "Fans");
	// electricalSubcat2.setDesc(desc2);
	// electricalSubcat2.setName("Fans");
	//
	// subCategoryDAO.insert(electricalSubcat2);
	//
	// SubCategory electricalSubcat3 = new SubCategory();
	// electricalSubcat3.setCategory(categories1.get(0));
	// Description desc3 = new Description("en", "Indoor Lighting");
	// electricalSubcat3.setDesc(desc3);
	// electricalSubcat3.setName("Indoor Lighting");
	//
	// subCategoryDAO.insert(electricalSubcat3);
	//
	// SubCategory electricalSubcat4 = new SubCategory();
	// electricalSubcat4.setCategory(categories1.get(0));
	// Description desc4 = new Description("en", "Outdoor Lighting");
	// electricalSubcat4.setDesc(desc4);
	// electricalSubcat4.setName("Outdoor Lighting");
	//
	// subCategoryDAO.insert(electricalSubcat4);
	//
	// SubCategory electricalSubcat5 = new SubCategory();
	// electricalSubcat5.setCategory(categories1.get(0));
	// Description desc5 = new Description("en",
	// "Switch Gears & Protection Devices");
	// electricalSubcat5.setDesc(desc5);
	// electricalSubcat5.setName("Switch Gears & Protection Devices");
	//
	// subCategoryDAO.insert(electricalSubcat5);
	//
	// SubCategory electricalSubcat6 = new SubCategory();
	// electricalSubcat6.setCategory(categories1.get(0));
	// Description desc6 = new Description("en", "Automatic Systems");
	// electricalSubcat6.setDesc(desc6);
	// electricalSubcat6.setName("Automatic Systems");
	//
	// subCategoryDAO.insert(electricalSubcat6);
	//
	// SubCategory electricalSubcat7 = new SubCategory();
	// electricalSubcat7.setCategory(categories1.get(0));
	// Description desc7 = new Description("en", "Accessories");
	// electricalSubcat7.setDesc(desc7);
	// electricalSubcat7.setName("Accessories");
	//
	// subCategoryDAO.insert(electricalSubcat7);
	// }
	// List<Category> categories2 = categoryDAO
	// .findByNameAllIgnoreCase("Painting");
	//
	// SubCategory paintingSubcat1 = new SubCategory();
	//
	// if (categories2 != null && categories2.size() != 0) {
	//
	// paintingSubcat1.setCategory(categories2.get(0));
	// Description desc1 = new Description("en",
	// "Interior Paints & Stains");
	// paintingSubcat1.setDesc(desc1);
	// paintingSubcat1.setName("Interior Paints & Stains");
	//
	// subCategoryDAO.insert(paintingSubcat1);
	//
	// SubCategory paintingSubcat2 = new SubCategory();
	// paintingSubcat2.setCategory(categories2.get(0));
	// Description desc2 = new Description("en",
	// "Exterior Paints & Stains");
	// paintingSubcat2.setDesc(desc2);
	// paintingSubcat2.setName("Exterior Paints & Stains");
	//
	// subCategoryDAO.insert(paintingSubcat2);
	//
	// SubCategory paintingSubcat3 = new SubCategory();
	// paintingSubcat3.setCategory(categories2.get(0));
	// Description desc3 = new Description("en", "Enamels");
	// paintingSubcat3.setDesc(desc3);
	// paintingSubcat3.setName("Enamels");
	//
	// subCategoryDAO.insert(paintingSubcat3);
	//
	// SubCategory paintingSubcat4 = new SubCategory();
	// paintingSubcat4.setCategory(categories2.get(0));
	// Description desc4 = new Description("en",
	// "Furniture & Craft Paints");
	// paintingSubcat4.setDesc(desc4);
	// paintingSubcat4.setName("Furniture & Craft Paints");
	//
	// subCategoryDAO.insert(paintingSubcat4);
	//
	// SubCategory paintingSubcat5 = new SubCategory();
	// paintingSubcat5.setCategory(categories2.get(0));
	// Description desc5 = new Description("en", "Metal Finishes");
	// paintingSubcat5.setDesc(desc5);
	// paintingSubcat5.setName("Metal Finishes");
	//
	// subCategoryDAO.insert(paintingSubcat5);
	//
	// SubCategory paintingSubcat6 = new SubCategory();
	// paintingSubcat6.setCategory(categories2.get(0));
	// Description desc6 = new Description("en", "Wood Finishes");
	// paintingSubcat6.setDesc(desc6);
	// paintingSubcat6.setName("Wood Finishes");
	//
	// subCategoryDAO.insert(paintingSubcat6);
	//
	// SubCategory paintingSubcat7 = new SubCategory();
	// paintingSubcat7.setCategory(categories2.get(0));
	// Description desc7 = new Description("en",
	// "Wall finishes and Effects");
	// paintingSubcat7.setDesc(desc7);
	// paintingSubcat7.setName("Wall finishes and Effects");
	//
	// subCategoryDAO.insert(paintingSubcat7);
	//
	// SubCategory paintingSubcat8 = new SubCategory();
	// paintingSubcat8.setCategory(categories2.get(0));
	// Description desc8 = new Description("en", "Under coats");
	// paintingSubcat8.setDesc(desc8);
	// paintingSubcat8.setName("Under coats");
	//
	// subCategoryDAO.insert(paintingSubcat8);
	//
	// SubCategory paintingSubcat9 = new SubCategory();
	// paintingSubcat9.setCategory(categories2.get(0));
	// Description desc9 = new Description("en", "Water Proofing");
	// paintingSubcat9.setDesc(desc9);
	// paintingSubcat9.setName("Water Proofing");
	//
	// subCategoryDAO.insert(paintingSubcat9);
	//
	// SubCategory paintingSubcat10 = new SubCategory();
	// paintingSubcat10.setCategory(categories2.get(0));
	// Description desc10 = new Description("en", "Accessories");
	// paintingSubcat10.setDesc(desc10);
	// paintingSubcat10.setName("Accessories");
	//
	// subCategoryDAO.insert(paintingSubcat10);
	// }
	//
	// List<Category> categories3 = categoryDAO
	// .findByNameAllIgnoreCase("Doors and Windows");
	//
	// SubCategory doorSubcat1 = new SubCategory();
	//
	// if (categories3 != null && categories3.size() != 0) {
	//
	// doorSubcat1.setCategory(categories3.get(0));
	// Description desc1 = new Description("en", "Doors");
	// doorSubcat1.setDesc(desc1);
	// doorSubcat1.setName("Doors");
	//
	// subCategoryDAO.insert(doorSubcat1);
	//
	// SubCategory doorSubcat2 = new SubCategory();
	// doorSubcat2.setCategory(categories3.get(0));
	// Description desc2 = new Description("en", "Windows");
	// doorSubcat2.setDesc(desc2);
	// doorSubcat2.setName("Windows");
	//
	// subCategoryDAO.insert(doorSubcat2);
	//
	// SubCategory doorSubcat3 = new SubCategory();
	// doorSubcat3.setCategory(categories3.get(0));
	// Description desc3 = new Description("en", "Ventilators");
	// doorSubcat3.setDesc(desc3);
	// doorSubcat3.setName("Ventilators");
	//
	// subCategoryDAO.insert(doorSubcat3);
	//
	// SubCategory doorSubcat4 = new SubCategory();
	// doorSubcat4.setCategory(categories3.get(0));
	// Description desc4 = new Description("en", "Gates");
	// doorSubcat4.setDesc(desc4);
	// doorSubcat4.setName("Gates");
	//
	// subCategoryDAO.insert(doorSubcat4);
	//
	// SubCategory doorSubcat5 = new SubCategory();
	// doorSubcat5.setCategory(categories3.get(0));
	// Description desc5 = new Description("en", "Safe Doors");
	// doorSubcat5.setDesc(desc5);
	// doorSubcat5.setName("Safe Doors");
	//
	// subCategoryDAO.insert(doorSubcat5);
	//
	// SubCategory doorSubcat6 = new SubCategory();
	// doorSubcat6.setCategory(categories3.get(0));
	// Description desc6 = new Description("en", "Glass doors");
	// doorSubcat6.setDesc(desc6);
	// doorSubcat6.setName("Glass doors");
	//
	// subCategoryDAO.insert(doorSubcat6);
	//
	// SubCategory doorSubcat7 = new SubCategory();
	// doorSubcat7.setCategory(categories3.get(0));
	// Description desc7 = new Description("en", "Accessories");
	// doorSubcat7.setDesc(desc7);
	// doorSubcat7.setName("Accessories");
	//
	// subCategoryDAO.insert(doorSubcat7);
	// }
	//
	// List<Category> categories4 = categoryDAO
	// .findByNameAllIgnoreCase("Surface Finishes");
	//
	// SubCategory surfaceSubcat1 = new SubCategory();
	//
	// if (categories4 != null && categories4.size() != 0) {
	//
	// surfaceSubcat1.setCategory(categories4.get(0));
	// Description desc1 = new Description("en", "Interior Flooring");
	// surfaceSubcat1.setDesc(desc1);
	// surfaceSubcat1.setName("Interior Flooring");
	//
	// subCategoryDAO.insert(surfaceSubcat1);
	//
	// SubCategory surfaceSubcat2 = new SubCategory();
	// surfaceSubcat2.setCategory(categories4.get(0));
	// Description desc2 = new Description("en", "Exterior Walls");
	// surfaceSubcat2.setDesc(desc2);
	// surfaceSubcat2.setName("Exterior Walls");
	// subCategoryDAO.insert(surfaceSubcat2);
	//
	// SubCategory surfaceSubcat3 = new SubCategory();
	// surfaceSubcat3.setCategory(categories4.get(0));
	// Description desc3 = new Description("en", "Interior Roofing");
	// surfaceSubcat3.setDesc(desc3);
	// surfaceSubcat3.setName("Interior Roofing");
	//
	// subCategoryDAO.insert(surfaceSubcat3);
	//
	// SubCategory surfaceSubcat4 = new SubCategory();
	// surfaceSubcat4.setCategory(categories4.get(0));
	// Description desc4 = new Description("en", "Exterior Flooring");
	// surfaceSubcat4.setDesc(desc4);
	// surfaceSubcat4.setName("Exterior Flooring");
	//
	// subCategoryDAO.insert(surfaceSubcat4);
	//
	// SubCategory surfaceSubcat5 = new SubCategory();
	// surfaceSubcat5.setCategory(categories4.get(0));
	// Description desc5 = new Description("en", "Exterior Roofing");
	// surfaceSubcat5.setDesc(desc5);
	// surfaceSubcat5.setName("Exterior Roofing");
	//
	// subCategoryDAO.insert(surfaceSubcat5);
	//
	// SubCategory surfaceSubcat6 = new SubCategory();
	// surfaceSubcat6.setCategory(categories4.get(0));
	// Description desc6 = new Description("en", "Counterd & pedestrals");
	// surfaceSubcat6.setDesc(desc6);
	// surfaceSubcat6.setName("Counterd & pedestrals");
	//
	// subCategoryDAO.insert(surfaceSubcat6);
	//
	// SubCategory surfaceSubcat7 = new SubCategory();
	// surfaceSubcat7.setCategory(categories4.get(0));
	// Description desc7 = new Description("en", "Accessories");
	// surfaceSubcat7.setDesc(desc7);
	// surfaceSubcat7.setName("Accessories");
	//
	// subCategoryDAO.insert(surfaceSubcat7);
	// }
	//
	// List<Category> categories5 = categoryDAO
	// .findByNameAllIgnoreCase("Interiors");
	//
	// SubCategory interiorSubcat1 = new SubCategory();
	//
	// if (categories5 != null && categories5.size() != 0) {
	//
	// interiorSubcat1.setCategory(categories5.get(0));
	// Description desc1 = new Description("en", "Office Furnitures");
	// interiorSubcat1.setDesc(desc1);
	// interiorSubcat1.setName("Office Furnitures");
	//
	// subCategoryDAO.insert(interiorSubcat1);
	//
	// SubCategory interiorSubcat2 = new SubCategory();
	// interiorSubcat2.setCategory(categories5.get(0));
	// Description desc2 = new Description("en", "Home Furnitures");
	// interiorSubcat2.setDesc(desc2);
	// interiorSubcat2.setName("Home Furnitures");
	//
	// subCategoryDAO.insert(interiorSubcat2);
	//
	// SubCategory interiorSubcat3 = new SubCategory();
	// interiorSubcat3.setCategory(categories5.get(0));
	// Description desc3 = new Description("en", "Hospital Furnitures");
	// interiorSubcat3.setDesc(desc3);
	// interiorSubcat3.setName("Hospital Furnitures");
	//
	// subCategoryDAO.insert(interiorSubcat3);
	//
	// SubCategory interiorSubcat4 = new SubCategory();
	// interiorSubcat4.setCategory(categories5.get(0));
	// Description desc4 = new Description("en",
	// "Institutional Furnitures");
	// interiorSubcat4.setDesc(desc4);
	// interiorSubcat4.setName("Institutional Furnitures");
	//
	// subCategoryDAO.insert(interiorSubcat4);
	//
	// SubCategory interiorSubcat5 = new SubCategory();
	// interiorSubcat5.setCategory(categories5.get(0));
	// Description desc5 = new Description("en", "Art and Decor");
	// interiorSubcat5.setDesc(desc5);
	// interiorSubcat5.setName("Art and Decor");
	//
	// subCategoryDAO.insert(interiorSubcat5);
	//
	// SubCategory interiorSubcat6 = new SubCategory();
	// interiorSubcat6.setCategory(categories5.get(0));
	// Description desc6 = new Description("en", "Mirrors");
	// interiorSubcat6.setDesc(desc6);
	// interiorSubcat6.setName("Mirrors");
	//
	// subCategoryDAO.insert(interiorSubcat6);
	//
	// SubCategory interiorSubcat7 = new SubCategory();
	// interiorSubcat7.setCategory(categories5.get(0));
	// Description desc7 = new Description("en", "Add ons");
	// interiorSubcat7.setDesc(desc7);
	// interiorSubcat7.setName("Add ons");
	//
	// subCategoryDAO.insert(interiorSubcat7);
	//
	// SubCategory interiorSubcat8 = new SubCategory();
	// interiorSubcat8.setCategory(categories5.get(0));
	// Description desc8 = new Description("en", "Accessories");
	// interiorSubcat8.setDesc(desc8);
	// interiorSubcat8.setName("Accessories");
	//
	// subCategoryDAO.insert(interiorSubcat8);
	// }
	//
	// List<Category> categories6 = categoryDAO
	// .findByNameAllIgnoreCase("Security Systems");
	//
	// SubCategory securitySubcat1 = new SubCategory();
	//
	// if (categories6 != null && categories6.size() != 0) {
	//
	// securitySubcat1.setCategory(categories6.get(0));
	// Description desc1 = new Description("en", "Safes");
	// securitySubcat1.setDesc(desc1);
	// securitySubcat1.setName("Safes");
	//
	// subCategoryDAO.insert(securitySubcat1);
	//
	// SubCategory securitySubcat2 = new SubCategory();
	// securitySubcat2.setCategory(categories6.get(0));
	// Description desc2 = new Description("en", "Survillance");
	// securitySubcat2.setDesc(desc2);
	// securitySubcat2.setName("Survillance");
	//
	// subCategoryDAO.insert(securitySubcat2);
	//
	// SubCategory securitySubcat3 = new SubCategory();
	// securitySubcat3.setCategory(categories6.get(0));
	// Description desc3 = new Description("en", "Fire Safety");
	// securitySubcat3.setDesc(desc3);
	// securitySubcat3.setName("Fire Safety");
	//
	// subCategoryDAO.insert(securitySubcat3);
	//
	// SubCategory securitySubcat4 = new SubCategory();
	// securitySubcat4.setCategory(categories6.get(0));
	// Description desc4 = new Description("en", "Door Security");
	// securitySubcat4.setDesc(desc4);
	// securitySubcat4.setName("Door Security");
	//
	// subCategoryDAO.insert(securitySubcat4);
	//
	// SubCategory securitySubcat5 = new SubCategory();
	// securitySubcat5.setCategory(categories6.get(0));
	// Description desc5 = new Description("en", "Accessories");
	// securitySubcat5.setDesc(desc5);
	// securitySubcat5.setName("Accessories");
	//
	// subCategoryDAO.insert(securitySubcat5);
	// }
	//
	// List<Category> categories7 = categoryDAO
	// .findByNameAllIgnoreCase("Building Materials");
	//
	// SubCategory buildingMaterialsSubcat1 = new SubCategory();
	//
	// if (categories7 != null && categories7.size() != 0) {
	//
	// buildingMaterialsSubcat1.setCategory(categories7.get(0));
	// Description desc1 = new Description("en", "Bricks and Blocks");
	// buildingMaterialsSubcat1.setDesc(desc1);
	// buildingMaterialsSubcat1.setName("Bricks and Blocks");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat1);
	//
	// SubCategory buildingMaterialsSubcat2 = new SubCategory();
	// buildingMaterialsSubcat2.setCategory(categories7.get(0));
	// Description desc2 = new Description("en", "Sand");
	// buildingMaterialsSubcat2.setDesc(desc2);
	// buildingMaterialsSubcat2.setName("Sand");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat2);
	//
	// SubCategory buildingMaterialsSubcat3 = new SubCategory();
	// buildingMaterialsSubcat3.setCategory(categories7.get(0));
	// Description desc3 = new Description("en", "Aggregates");
	// buildingMaterialsSubcat3.setDesc(desc3);
	// buildingMaterialsSubcat3.setName("Aggregates");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat3);
	//
	// SubCategory buildingMaterialsSubcat4 = new SubCategory();
	// buildingMaterialsSubcat4.setCategory(categories7.get(0));
	// Description desc4 = new Description("en", "RMC");
	// buildingMaterialsSubcat4.setDesc(desc4);
	// buildingMaterialsSubcat4.setName("RMC");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat4);
	//
	// SubCategory buildingMaterialsSubcat5 = new SubCategory();
	// buildingMaterialsSubcat5.setCategory(categories7.get(0));
	// Description desc5 = new Description("en", "Steel");
	// buildingMaterialsSubcat5.setDesc(desc5);
	// buildingMaterialsSubcat5.setName("Steel");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat5);
	//
	// SubCategory buildingMaterialsSubcat6 = new SubCategory();
	// buildingMaterialsSubcat6.setCategory(categories7.get(0));
	// Description desc6 = new Description("en", "Roofing");
	// buildingMaterialsSubcat6.setDesc(desc6);
	// buildingMaterialsSubcat6.setName("Roofing");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat6);
	//
	// SubCategory buildingMaterialsSubcat7 = new SubCategory();
	// buildingMaterialsSubcat7.setCategory(categories7.get(0));
	// Description desc7 = new Description("en", "Insulation");
	// buildingMaterialsSubcat7.setDesc(desc7);
	// buildingMaterialsSubcat7.setName("Insulation");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat7);
	//
	// SubCategory buildingMaterialsSubcat8 = new SubCategory();
	// buildingMaterialsSubcat8.setCategory(categories7.get(0));
	// Description desc8 = new Description("en", "Moulding");
	// buildingMaterialsSubcat8.setDesc(desc8);
	// buildingMaterialsSubcat8.setName("Moulding");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat8);
	//
	// SubCategory buildingMaterialsSubcat9 = new SubCategory();
	// buildingMaterialsSubcat9.setCategory(categories7.get(0));
	// Description desc9 = new Description("en", "Pnneling and Dry Walls");
	// buildingMaterialsSubcat9.setDesc(desc9);
	// buildingMaterialsSubcat9.setName("Pnneling and Dry Walls");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat9);
	//
	// SubCategory buildingMaterialsSubcat10 = new SubCategory();
	// buildingMaterialsSubcat10.setCategory(categories7.get(0));
	// Description desc10 = new Description("en", "Filler and Finishers");
	// buildingMaterialsSubcat10.setDesc(desc10);
	// buildingMaterialsSubcat10.setName("Filler and Finishers");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat10);
	//
	// SubCategory buildingMaterialsSubcat11 = new SubCategory();
	// buildingMaterialsSubcat11.setCategory(categories7.get(0));
	// Description desc11 = new Description("en", "Wood and Lumber");
	// buildingMaterialsSubcat11.setDesc(desc11);
	// buildingMaterialsSubcat11.setName("Wood and Lumber");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat11);
	//
	// SubCategory buildingMaterialsSubcat12 = new SubCategory();
	// buildingMaterialsSubcat12.setCategory(categories7.get(0));
	// Description desc12 = new Description("en", "Accessories");
	// buildingMaterialsSubcat12.setDesc(desc12);
	// buildingMaterialsSubcat12.setName("Accessories");
	//
	// subCategoryDAO.insert(buildingMaterialsSubcat12);
	// }
	//
	// List<Category> categories8 = categoryDAO
	// .findByNameAllIgnoreCase("Landsacpe");
	//
	// SubCategory landscapeSubcat1 = new SubCategory();
	//
	// if (categories8 != null && categories8.size() != 0) {
	//
	// landscapeSubcat1.setCategory(categories8.get(0));
	// Description desc1 = new Description("en", "Garden tools");
	// landscapeSubcat1.setDesc(desc1);
	// landscapeSubcat1.setName("Garden tools");
	//
	// subCategoryDAO.insert(landscapeSubcat1);
	//
	// SubCategory landscapeSubcat2 = new SubCategory();
	// landscapeSubcat2.setCategory(categories8.get(0));
	// Description desc2 = new Description("en",
	// "Lawn and Plants/ Soft scape");
	// landscapeSubcat2.setDesc(desc2);
	// landscapeSubcat2.setName("Lawn and Plants/ Soft scape");
	//
	// subCategoryDAO.insert(landscapeSubcat2);
	//
	// SubCategory landscapeSubcat3 = new SubCategory();
	// landscapeSubcat3.setCategory(categories8.get(0));
	// Description desc3 = new Description("en", "Fencing");
	// landscapeSubcat3.setDesc(desc3);
	// landscapeSubcat3.setName("Fencing");
	//
	// subCategoryDAO.insert(landscapeSubcat3);
	//
	// SubCategory landscapeSubcat4 = new SubCategory();
	// landscapeSubcat4.setCategory(categories8.get(0));
	// Description desc4 = new Description("en", "Decking");
	// landscapeSubcat4.setDesc(desc4);
	// landscapeSubcat4.setName("Decking");
	//
	// subCategoryDAO.insert(landscapeSubcat4);
	//
	// SubCategory landscapeSubcat5 = new SubCategory();
	// landscapeSubcat5.setCategory(categories8.get(0));
	// Description desc5 = new Description("en", "Pet Spaces");
	// landscapeSubcat5.setDesc(desc5);
	// landscapeSubcat5.setName("Pet Spaces");
	//
	// subCategoryDAO.insert(landscapeSubcat5);
	//
	// SubCategory landscapeSubcat6 = new SubCategory();
	// landscapeSubcat6.setCategory(categories8.get(0));
	// Description desc6 = new Description("en", "Hardscape");
	// landscapeSubcat6.setDesc(desc6);
	// landscapeSubcat6.setName("Hardscape");
	//
	// subCategoryDAO.insert(landscapeSubcat6);
	//
	// SubCategory landscapeSubcat7 = new SubCategory();
	// landscapeSubcat7.setCategory(categories8.get(0));
	// Description desc7 = new Description("en", "Watering and Irrigation");
	// landscapeSubcat7.setDesc(desc7);
	// landscapeSubcat7.setName("Watering and Irrigation");
	//
	// subCategoryDAO.insert(landscapeSubcat7);
	//
	// SubCategory landscapeSubcat8 = new SubCategory();
	// landscapeSubcat8.setCategory(categories8.get(0));
	// Description desc8 = new Description("en", "Pots and Planters");
	// landscapeSubcat8.setDesc(desc8);
	// landscapeSubcat8.setName("Pots and Planters");
	//
	// subCategoryDAO.insert(landscapeSubcat8);
	//
	// SubCategory landscapeSubcat9 = new SubCategory();
	// landscapeSubcat9.setCategory(categories8.get(0));
	// Description desc9 = new Description("en",
	// "Soil seeds and fertilizers");
	// landscapeSubcat9.setDesc(desc9);
	// landscapeSubcat9.setName("Soil seeds and fertilizers");
	//
	// subCategoryDAO.insert(landscapeSubcat9);
	//
	// SubCategory landscapeSubcat10 = new SubCategory();
	// landscapeSubcat10.setCategory(categories8.get(0));
	// Description desc10 = new Description("en", "Accessories");
	// landscapeSubcat10.setDesc(desc10);
	// landscapeSubcat10.setName("Accessories");
	//
	// subCategoryDAO.insert(landscapeSubcat10);
	// }
	//
	// List<Category> categories9 = categoryDAO
	// .findByNameAllIgnoreCase("Tools and Hardware");
	//
	// SubCategory toolsSubcat1 = new SubCategory();
	//
	// if (categories9 != null && categories9.size() != 0) {
	//
	// toolsSubcat1.setCategory(categories9.get(0));
	// Description desc1 = new Description("en", "Power tools");
	// toolsSubcat1.setDesc(desc1);
	// toolsSubcat1.setName("Power tools");
	//
	// subCategoryDAO.insert(toolsSubcat1);
	//
	// SubCategory toolsSubcat2 = new SubCategory();
	// toolsSubcat2.setCategory(categories9.get(0));
	// Description desc2 = new Description("en", "Hand tools");
	// toolsSubcat2.setDesc(desc2);
	// toolsSubcat2.setName("Hand tools");
	//
	// subCategoryDAO.insert(toolsSubcat2);
	//
	// SubCategory toolsSubcat3 = new SubCategory();
	// toolsSubcat3.setCategory(categories9.get(0));
	// Description desc3 = new Description("en", "Automative");
	// toolsSubcat3.setDesc(desc3);
	// toolsSubcat3.setName("Automative");
	//
	// subCategoryDAO.insert(toolsSubcat3);
	//
	// SubCategory toolsSubcat4 = new SubCategory();
	// toolsSubcat4.setCategory(categories9.get(0));
	// Description desc4 = new Description("en", "Hardware");
	// toolsSubcat4.setDesc(desc4);
	// toolsSubcat4.setName("Hardware");
	//
	// subCategoryDAO.insert(toolsSubcat4);
	//
	// SubCategory toolsSubcat5 = new SubCategory();
	// toolsSubcat5.setCategory(categories9.get(0));
	// Description desc5 = new Description("en",
	// "Workware and Safety gare");
	// toolsSubcat5.setDesc(desc5);
	// toolsSubcat5.setName("Workware and Safety gare");
	//
	// subCategoryDAO.insert(toolsSubcat5);
	//
	// SubCategory toolsSubcat6 = new SubCategory();
	// toolsSubcat6.setCategory(categories9.get(0));
	// Description desc6 = new Description("en", "Utility Hardware");
	// toolsSubcat6.setDesc(desc6);
	// toolsSubcat6.setName("Utility Hardware");
	//
	// subCategoryDAO.insert(toolsSubcat6);
	//
	// SubCategory toolsSubcat7 = new SubCategory();
	// toolsSubcat7.setCategory(categories9.get(0));
	// Description desc7 = new Description("en", "Fastners");
	// toolsSubcat7.setDesc(desc7);
	// toolsSubcat7.setName("Fastners");
	//
	// subCategoryDAO.insert(toolsSubcat7);
	//
	// SubCategory toolsSubcat8 = new SubCategory();
	// toolsSubcat8.setCategory(categories9.get(0));
	// Description desc8 = new Description("en", "Tools Storage");
	// toolsSubcat8.setDesc(desc8);
	// toolsSubcat8.setName("Tools Storage");
	//
	// subCategoryDAO.insert(toolsSubcat8);
	//
	// SubCategory toolsSubcat9 = new SubCategory();
	// toolsSubcat9.setCategory(categories9.get(0));
	// Description desc9 = new Description("en", "Accessories");
	// toolsSubcat9.setDesc(desc9);
	// toolsSubcat9.setName("Accessories");
	//
	// subCategoryDAO.insert(toolsSubcat9);
	// }
	//
	// List<Category> categories10 = categoryDAO
	// .findByNameAllIgnoreCase("Appliances");
	//
	// SubCategory appliancesSubcat1 = new SubCategory();
	//
	// if (categories10 != null && categories10.size() != 0) {
	//
	// appliancesSubcat1.setCategory(categories10.get(0));
	// Description desc1 = new Description("en", "Gysers");
	// appliancesSubcat1.setDesc(desc1);
	// appliancesSubcat1.setName("Gysers");
	//
	// subCategoryDAO.insert(appliancesSubcat1);
	//
	// SubCategory appliancesSubcat2 = new SubCategory();
	// appliancesSubcat2.setCategory(categories10.get(0));
	// Description desc2 = new Description("en", "Refrigerators");
	// appliancesSubcat2.setDesc(desc2);
	// appliancesSubcat2.setName("Refrigerators");
	//
	// subCategoryDAO.insert(appliancesSubcat2);
	//
	// SubCategory appliancesSubcat3 = new SubCategory();
	// appliancesSubcat3.setCategory(categories10.get(0));
	// Description desc3 = new Description("en", "TV and Sounds");
	// appliancesSubcat3.setDesc(desc3);
	// appliancesSubcat3.setName("TV and Sounds");
	//
	// subCategoryDAO.insert(appliancesSubcat3);
	//
	// SubCategory appliancesSubcat4 = new SubCategory();
	// appliancesSubcat4.setCategory(categories10.get(0));
	// Description desc4 = new Description("en", "Washers and Dryers");
	// appliancesSubcat4.setDesc(desc4);
	// appliancesSubcat4.setName("Washers and Dryers");
	//
	// subCategoryDAO.insert(appliancesSubcat4);
	//
	// SubCategory appliancesSubcat5 = new SubCategory();
	// appliancesSubcat5.setCategory(categories10.get(0));
	// Description desc5 = new Description("en", "Vaccum Pumps");
	// appliancesSubcat5.setDesc(desc5);
	// appliancesSubcat5.setName("Vaccum Pumps");
	//
	// subCategoryDAO.insert(appliancesSubcat5);
	//
	// SubCategory appliancesSubcat6 = new SubCategory();
	// appliancesSubcat6.setCategory(categories10.get(0));
	// Description desc6 = new Description("en", "Microwave and Oven");
	// appliancesSubcat6.setDesc(desc6);
	// appliancesSubcat6.setName("Microwave and Oven");
	//
	// subCategoryDAO.insert(appliancesSubcat6);
	//
	// SubCategory appliancesSubcat7 = new SubCategory();
	// appliancesSubcat7.setCategory(categories10.get(0));
	// Description desc7 = new Description("en", "Hob and Hood");
	// appliancesSubcat7.setDesc(desc7);
	// appliancesSubcat7.setName("Hob and Hood");
	//
	// subCategoryDAO.insert(appliancesSubcat7);
	//
	// SubCategory appliancesSubcat8 = new SubCategory();
	// appliancesSubcat8.setCategory(categories10.get(0));
	// Description desc8 = new Description("en", "Ac and Coolers");
	// appliancesSubcat8.setDesc(desc8);
	// appliancesSubcat8.setName("Ac and Coolers");
	//
	// subCategoryDAO.insert(appliancesSubcat8);
	//
	// SubCategory appliancesSubcat9 = new SubCategory();
	// appliancesSubcat9.setCategory(categories10.get(0));
	// Description desc9 = new Description("en", "Accessories");
	// appliancesSubcat9.setDesc(desc9);
	// appliancesSubcat9.setName("Accessories");
	//
	// subCategoryDAO.insert(appliancesSubcat9);
	// }
	//
	// List<Category> categories11 = categoryDAO
	// .findByNameAllIgnoreCase("Mechanical");
	//
	// SubCategory mechanicalSubcat1 = new SubCategory();
	//
	// if (categories11 != null && categories11.size() != 0) {
	//
	// mechanicalSubcat1.setCategory(categories11.get(0));
	// Description desc1 = new Description("en",
	// "Elevators and Escalators");
	// mechanicalSubcat1.setDesc(desc1);
	// mechanicalSubcat1.setName("Elevators and Escalators");
	//
	// subCategoryDAO.insert(mechanicalSubcat1);
	//
	// SubCategory mechanicalSubcat2 = new SubCategory();
	// mechanicalSubcat2.setCategory(categories11.get(0));
	// Description desc2 = new Description("en", "HVAC");
	// mechanicalSubcat2.setDesc(desc2);
	// mechanicalSubcat2.setName("HVAC");
	//
	// subCategoryDAO.insert(mechanicalSubcat2);
	//
	// SubCategory mechanicalSubcat3 = new SubCategory();
	// mechanicalSubcat3.setCategory(categories11.get(0));
	// Description desc3 = new Description("en", "Generators");
	// mechanicalSubcat3.setDesc(desc3);
	// mechanicalSubcat3.setName("Generators");
	//
	// subCategoryDAO.insert(mechanicalSubcat3);
	//
	// SubCategory mechanicalSubcat4 = new SubCategory();
	// mechanicalSubcat4.setCategory(categories11.get(0));
	// Description desc4 = new Description("en", "UPS and Batteries");
	// mechanicalSubcat4.setDesc(desc4);
	// mechanicalSubcat4.setName("UPS and Batteries");
	//
	// subCategoryDAO.insert(mechanicalSubcat4);
	//
	// SubCategory mechanicalSubcat5 = new SubCategory();
	// mechanicalSubcat5.setCategory(categories11.get(0));
	// Description desc5 = new Description("en", "Solar");
	// mechanicalSubcat5.setDesc(desc5);
	// mechanicalSubcat5.setName("Solar");
	//
	// subCategoryDAO.insert(mechanicalSubcat5);
	//
	// SubCategory mechanicalSubcat6 = new SubCategory();
	// mechanicalSubcat6.setCategory(categories11.get(0));
	// Description desc6 = new Description("en",
	// "Pressure Pumps and Motors");
	// mechanicalSubcat6.setDesc(desc6);
	// mechanicalSubcat6.setName("Pressure Pumps and Motors");
	//
	// subCategoryDAO.insert(mechanicalSubcat6);
	//
	// SubCategory mechanicalSubcat7 = new SubCategory();
	// mechanicalSubcat7.setCategory(categories11.get(0));
	// Description desc7 = new Description("en", "Ecological Systems");
	// mechanicalSubcat7.setDesc(desc7);
	// mechanicalSubcat7.setName("Ecological Systems");
	//
	// subCategoryDAO.insert(mechanicalSubcat7);
	//
	// SubCategory mechanicalSubcat8 = new SubCategory();
	// mechanicalSubcat8.setCategory(categories11.get(0));
	// Description desc8 = new Description("en",
	// "Swimming Pool and Skimmares");
	// mechanicalSubcat8.setDesc(desc8);
	// mechanicalSubcat8.setName("Swimming Pool and Skimmares");
	//
	// subCategoryDAO.insert(mechanicalSubcat8);
	//
	// SubCategory mechanicalSubcat9 = new SubCategory();
	// mechanicalSubcat9.setCategory(categories11.get(0));
	// Description desc9 = new Description("en", "Accessories");
	// mechanicalSubcat9.setDesc(desc9);
	// mechanicalSubcat9.setName("Accessories");
	//
	// subCategoryDAO.insert(mechanicalSubcat9);
	// }
	// List<Category> categories12 = categoryDAO
	// .findByNameAllIgnoreCase("Service Providers");
	//
	// SubCategory servProvidersSubcat1 = new SubCategory();
	//
	// if (categories12 != null && categories12.size() != 0) {
	//
	// servProvidersSubcat1.setCategory(categories12.get(0));
	// Description desc1 = new Description("en", "Architects");
	// servProvidersSubcat1.setDesc(desc1);
	// servProvidersSubcat1.setName("Architects");
	//
	// subCategoryDAO.insert(servProvidersSubcat1);
	//
	// SubCategory servProvidersSubcat2 = new SubCategory();
	// servProvidersSubcat2.setCategory(categories12.get(0));
	// Description desc2 = new Description("en", "Interior Designer");
	// servProvidersSubcat2.setDesc(desc2);
	// servProvidersSubcat2.setName("Interior Designer");
	//
	// subCategoryDAO.insert(servProvidersSubcat2);
	//
	// SubCategory servProvidersSubcat3 = new SubCategory();
	// servProvidersSubcat3.setCategory(categories12.get(0));
	// Description desc3 = new Description("en", "Contractors");
	// servProvidersSubcat3.setDesc(desc3);
	// servProvidersSubcat3.setName("Contractors");
	//
	// subCategoryDAO.insert(servProvidersSubcat3);
	//
	// SubCategory servProvidersSubcat4 = new SubCategory();
	// servProvidersSubcat4.setCategory(categories12.get(0));
	// Description desc4 = new Description("en", "Engineers");
	// servProvidersSubcat4.setDesc(desc4);
	// servProvidersSubcat4.setName("Engineers");
	//
	// subCategoryDAO.insert(servProvidersSubcat4);
	//
	// SubCategory servProvidersSubcat5 = new SubCategory();
	// servProvidersSubcat5.setCategory(categories12.get(0));
	// Description desc5 = new Description("en", "Vaastu Consultants");
	// servProvidersSubcat5.setDesc(desc5);
	// servProvidersSubcat5.setName("Vaastu Consultants");
	//
	// subCategoryDAO.insert(servProvidersSubcat5);
	//
	// SubCategory servProvidersSubcat6 = new SubCategory();
	// servProvidersSubcat6.setCategory(categories12.get(0));
	// Description desc6 = new Description("en", "BIM Consultants");
	// servProvidersSubcat6.setDesc(desc6);
	// servProvidersSubcat6.setName("BIM Consultants");
	//
	// subCategoryDAO.insert(servProvidersSubcat6);
	//
	// SubCategory servProvidersSubcat7 = new SubCategory();
	// servProvidersSubcat7.setCategory(categories12.get(0));
	// Description desc7 = new Description("en", "Plumbers");
	// servProvidersSubcat7.setDesc(desc7);
	// servProvidersSubcat7.setName("Plumbers");
	//
	// subCategoryDAO.insert(servProvidersSubcat7);
	//
	// SubCategory servProvidersSubcat8 = new SubCategory();
	// servProvidersSubcat8.setCategory(categories12.get(0));
	// Description desc8 = new Description("en", "Electricians");
	// servProvidersSubcat8.setDesc(desc8);
	// servProvidersSubcat8.setName("Electricians");
	//
	// subCategoryDAO.insert(servProvidersSubcat8);
	//
	// }
	// }
}
