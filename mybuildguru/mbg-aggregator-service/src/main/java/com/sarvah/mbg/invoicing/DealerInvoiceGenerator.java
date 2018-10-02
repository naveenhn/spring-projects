package com.sarvah.mbg.invoicing;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;

import com.sarvah.mbg.commons.storage.AzureFileStorage;
import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.invoicing.model.Item;
import com.sarvah.mbg.invoicing.model.OrderForDealerInvoice;
import com.sarvah.mbg.invoicing.model.OrderInfoObjectForDealer;

class DealerTuple<T> {
	T t1;
	T t2;

	public DealerTuple(T t1, T t2) {
		this.t1 = t1;
		this.t2 = t2;
	}

	public DealerTuple(String t) {
		t1 = (T) t.toString().split(",")[0];
		t2 = (T) t.toString().split(",")[1];
	}
}

public class DealerInvoiceGenerator {

	@Autowired
	private FileStorage filestorage;

	public DealerInvoiceGenerator() {

	}

	static OrderForDealerInvoice order;
	PDDocument document;

	float tip;

	BaseTable table;
	float tableMargin = 30;
	float tableBottomMargin = 15;

	float cellHeight = 15;
	float tableWidth;
	int endOfPage = 110; // 110+30

	float padding = 5;
	boolean drawLines = true, drawContent = true;
	boolean drawLines_header = false;

	String emptyString = "";

	PDPage currPage;
	Row<?> currRow;
	Cell<?> currCell;

	/*
	 * public static void main(String[] args) {
	 * 
	 * UserInvoiceGenerator generator = new UserInvoiceGenerator();
	 * 
	 * OrderForInvoice order = new OrderForInvoice(16371, new Date(), 13546, new
	 * Date(), new Address("Billing Address details", "Line 1, Line1a",
	 * "Line 2 Line 2 Line 2", "Mysore", "Karnataka", "India", "560002"), new
	 * Address( "Shipping Addres details", "Line 1, Line1a",
	 * "Line 2 Line 2 Line 2", "Mysore", "Karnataka", "India", "560002"),
	 * "ABC logistics", "AYDGEU18383838", Arrays.asList(new Item("Item 1",
	 * 101.0, 2, 10.00f, 222.20, "raj", "123", "12354", "unit"), new
	 * Item("Item 2", 200.0, 25, 20.00f, 333.33, "MyBuildGuru", "456", "456789",
	 * "unit")), 10.0);
	 * 
	 * try { generator.generateInvoice(order); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	private void addHeader(List<String> headers) throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, drawLines_header, drawContent);
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight + padding);
		for (String header : headers) {
			currCell = currRow.createCell(100 / headers.size(), header,
					HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
			currCell.setFont(PDType1Font.HELVETICA_BOLD);
			currCell.setFontSize(11);
			// currCell.setFillColor(Color.LIGHT_GRAY);
		}
		table.draw();
	}

	private void addSimpleHeader(List<String> headers) throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, drawLines_header, drawContent);
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight + padding);
		for (String header : headers) {
			currCell = currRow.createCell(100 / headers.size(), header,
					HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
			currCell.setFont(PDType1Font.HELVETICA);
			currCell.setFontSize(9);
		}
		table.draw();
	}

	private void addMixed(List<DealerTuple<String>> line) throws IOException {
		// Redrawing table is needed since we do not need lines between the
		// sub-cells
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		currRow = table.createRow(cellHeight);
		float perCol = 135 / line.size();
		for (int i = 0; i < line.size(); i++) {
			DealerTuple<String> col = line.get(i);
			int l1 = col.t1.length();
			currCell = currRow.createCell(l1, col.t1.toString());
			currCell.setFont(PDType1Font.HELVETICA_BOLD);
			currCell = currRow.createCell(perCol - l1, "");
		}
		table.draw();
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		currRow = table.createRow(cellHeight);
		for (int i = 0; i < line.size(); i++) {
			DealerTuple<String> col = line.get(i);
			int l1 = col.t1.length();
			int l2 = col.t2.length();
			currCell = currRow.createCell(10, "");
			currCell = currRow.createCell(perCol - l1, col.t2.toString());
		}
		table.draw();
		tip = tip - (cellHeight);
	}

	private void newPage() {
		currPage = new PDPage();
		document.addPage(currPage);
		tableWidth = currPage.getMediaBox().getWidth() - (2 * tableMargin);
		tip = currPage.getMediaBox().getHeight() - (2 * tableMargin);
	}

	private void addInvoiceHeader() throws IOException {
		addHeader(Arrays.asList("ORDER COPY"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");
		Date invoiceDate = order.getInvoiceDate();

		String dateInString = sdf.format(invoiceDate);

		addSimpleHeader(Arrays.asList(order.getInvoiceId() + "  -  "
				+ dateInString));
	}

	private void addBufferLine() {
		tip = tip - padding;
	}

	private void addTopMargin() {
		addBufferLine();
		addBufferLine();
		addBufferLine();
		addBufferLine();
	}

	@SuppressWarnings("unchecked")
	private void addInvoiceMetaData() throws IOException, ParseException {
		tip = tip - padding;
		line();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");
		Date orderDate = order.getOrderDate();

		String dateInString = sdf.format(orderDate);

		addRegularFontForHeader(Arrays.asList("Order Placed on: "
				+ dateInString, emptyString,
				"Delivery Preference: " + order.getDeliveryPreference()));
		addRegularFontForHeader(Arrays.asList(
				"Order Id: " + order.getOrderId(), emptyString,
				"Payment Method: " + order.getPaymentMethod()));

		line();
		tip = tip - padding;
	}

	private void addBox(List<String> headers) throws IOException {
		line();
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, false, drawContent);
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight + padding);
		for (String header : headers) {
			currCell = currRow.createCell(100 / headers.size(), header,
					HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
			currCell.setFont(PDType1Font.HELVETICA_BOLD);
			currCell.setFontSize(9);
		}
		table.draw();
		line();
	}

	private void addImage() {
		try {
			PDPageContentStream contents = new PDPageContentStream(document,
					currPage);

			PDImageXObject image = PDImageXObject.createFromFile(
					"/home/mowgli/Logo.png", document);

			// PDImageXObject image = PDImageXObject.createFromFile(
			// "D:/mybuildguru/Logo.png", document);

			contents.drawImage(image, tableMargin, tip, 202, 42);
			contents.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addRow(List<String> text, PDType1Font f) throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		// print "A       " "B" "C   "
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight);
		for (int i = 0; i < text.size(); i++) {
			currCell = currRow.createCell(100 / text.size() * (i + 1),
					text.get(i));
			currCell.setFont(f);
			currCell.setFontSize(9);
		}
		table.draw();
	}

	private void addRowCustom(List<String> text, PDType1Font f)
			throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		// print "A       " "B" "C   "
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight);
		for (int i = 0; i < text.size(); i++) {
			currCell = currRow.createCell(80 / text.size() * (i + 1),
					text.get(i));
			currCell.setFont(f);
			currCell.setFontSize(9);
		}
		table.draw();
	}

	private void addRowCustomForTotal(List<String> text, PDType1Font f)
			throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		// print "A       " "B" "C   "
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight);
		for (int i = 0; i < text.size(); i++) {
			currCell = currRow.createCell(92 / text.size() * (i + 1),
					text.get(i));
			currCell.setFont(f);
			currCell.setFontSize(11);
		}
		table.draw();
	}

	private void addRowCustomForTotalSavings(List<String> text, PDType1Font f)
			throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		// print "A       " "B" "C   "
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight);
		for (int i = 0; i < text.size(); i++) {
			currCell = currRow.createCell(92 / text.size() * (i + 1),
					text.get(i));
			currCell.setFont(f);
		}
		table.draw();
	}

	private void addRowCustomForHeader(List<String> text, PDType1Font f)
			throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		// print "A       " "B" "C   "
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight);
		for (int i = 0; i < text.size(); i++) {
			currCell = currRow.createCell(73 / text.size() * (i + 1),
					text.get(i));
			currCell.setFont(f);
		}
		table.draw();
	}

	private void addWeightedRow(String texts, PDType1Font f) throws IOException {
		List<String> chunks = Arrays.asList(texts.split(","));
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, !drawLines, drawContent);
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight);
		for (int i = 0; i < chunks.size(); i++) {
			currCell = currRow.createCell(100 / texts.length()
					* chunks.get(i).length(), chunks.get(i));
			currCell.setFont(f);
		}
		table.draw();
	}

	private void addRow(List<String> text) throws IOException {
		addRow(text, PDType1Font.HELVETICA);
	}

	private void addBoldFont(List<String> text) throws IOException {
		addRow(text, PDType1Font.HELVETICA_BOLD);
	}

	private void addRegularFont(List<String> text) throws IOException {
		addRow(text);
	}

	private void addBoldFontCustom(List<String> text) throws IOException {
		addRowCustom(text, PDType1Font.HELVETICA_BOLD);
	}

	private void addRegularFontCustom(List<String> text) throws IOException {
		addRowCustom(text, PDType1Font.HELVETICA);
	}

	private void addRegularFontForHeader(List<String> text) throws IOException {
		addRowCustomForHeader(text, PDType1Font.HELVETICA);
	}

	private void addRegularFontCustomForTotalAmount(List<String> text)
			throws IOException {
		addRowCustomForTotal(text, PDType1Font.HELVETICA.HELVETICA_BOLD);
	}

	private void addRegularFontCustomForTotalSavings(List<String> text)
			throws IOException {
		addRowCustomForTotalSavings(text, PDType1Font.HELVETICA.HELVETICA);
	}

	@SuppressWarnings("unchecked")
	private void addBuyerSellerBlock() throws IOException {
		if (order.getBuyerAddress() != null) {

			addBoldFontCustom(Arrays.asList("Customer Details", emptyString,
					"Delivery Address"));
			addRegularFontCustom(Arrays.asList(order.getBuyerAddress()
					.getName(), emptyString, order.getShippingAddress()
					.getName()));
			addRegularFontCustom(Arrays.asList(order.getBuyerAddress()
					.getAddressLine1(), emptyString, order.getShippingAddress()
					.getAddressLine1()));
			addRegularFontCustom(Arrays.asList(order.getBuyerAddress()
					.getCity(), emptyString, order.getShippingAddress()
					.getCity()));
			addRegularFontCustom(Arrays.asList(order.getBuyerAddress()
					.getState(), emptyString, order.getShippingAddress()
					.getState()));
			addRegularFontCustom(Arrays.asList(
					order.getBuyerAddress().getZip(), emptyString, order
							.getShippingAddress().getZip()));
			addRegularFontCustom(Arrays.asList(order.getBuyerAddress()
					.getMobile(), emptyString, order.getShippingAddress()
					.getMobile()));

		}

	}

	private void line() throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, drawLines, drawContent);
		Row<?> row = table.createRow(padding);
		table.draw();
	}

	private void addDispatchInfo() throws IOException {
		tip = tip - padding;
		line();
		// addMixed(Arrays.asList(new Tuple("Dispatched By:", order.items),
		// new Tuple("Dispatch Ref Number:", order.dispatchId)));
		line();
		tip = tip - 2 * padding;
	}

	private void drawTableWithHeader(String headers, List<String> items)
			throws IOException {
		drawTableWithHeader(headers, items, drawLines_header);
	}

	private void printHeader(String headers, List<Float> cellRatios) {
		for (String header : headers.split(",")) {
			float ratio = new Float(108 * header.length()) / headers.length();
			cellRatios.add(ratio);
			currCell = currRow.createCell(ratio, header,
					HorizontalAlignment.LEFT, VerticalAlignment.MIDDLE);
			currCell.setFont(PDType1Font.HELVETICA_BOLD);
			currCell.setFontSize(9);
			currCell.setFillColor(Color.LIGHT_GRAY);
		}
	}

	private void drawTableWithHeader(String headers, List<String> items,
			boolean drawLines) throws IOException {
		table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
				tableMargin, document, currPage, drawLines, drawContent);
		currRow = table.createRow(cellHeight);
		tip = tip - (cellHeight + padding);

		List<Float> cellRatios = new ArrayList<>();

		printHeader(headers, cellRatios);

		for (String item : items) {
			if (tip < endOfPage) {
				table.draw();
				newPage();
				table = new BaseTable(tip, tip, tableBottomMargin, tableWidth,
						tableMargin, document, currPage, drawLines, drawContent);
				currRow = table.createRow(cellHeight);
				int delta = 10;
				tip = tip - (cellHeight + padding + delta);
				printHeader(headers, cellRatios);
			}
			currRow = table.createRow(cellHeight);
			int delta = 10;
			tip = tip - (cellHeight + padding + delta);
			List<String> chunks = Arrays.asList(item.split(","));
			for (int i = 0; i < chunks.size(); i++) {
				currCell = currRow.createCell(cellRatios.get(i), chunks.get(i),
						HorizontalAlignment.LEFT, VerticalAlignment.MIDDLE);
				currCell.setFontSize(9);
			}
		}
		table.draw();
	}

	private void addItemTable() throws IOException {
		// determine ratios
		// String header =
		// "S.No.,    Item Description      ,Qty ,   Seller   ,Unit Price,Del. Chrgs, Disc(%), Amount";
		String header = " Sl.No,     Item Description       , Qty   ,    Seller    ,Unit Price,Del. Chrgs,Disc(%), Amount ";
		// String total = "       Total      " + "," + order.total.toString();

		List<String> itemString = new ArrayList<>();
		List<Item> itemsList = new ArrayList<Item>(order.items);
		for (int i = 0; i < itemsList.size(); i++) {
			Item item = itemsList.get(i);

			itemString.add((i + 1) + "," + item.getName() + ","
					+ item.getQtyType() + "," + item.getSellerName() + ","
					+ item.getRate() + "," + item.getDeliveryCharge() + ","
					+ item.getDiscount() + "," + item.getAmount());
		}
		// draw header
		// draw table
		drawTableWithHeader(header, itemString, !drawLines);
		tip = tip - (cellHeight + padding);
		line();
		// drawTableWithHeader(total, new ArrayList<>(), !drawLines);
	}

	@SuppressWarnings("unchecked")
	private void addTotalMetaData() throws IOException, ParseException {
		addRegularFontCustomForTotalAmount(Arrays.asList("TOTAL AMOUNT ", "",
				order.getTotalAmount()));
	}

	private void addDeclaration(String declaration) throws IOException {
		tip = tip - padding;
		addWeightedRow(declaration, PDType1Font.HELVETICA);
		tip = tip - padding;
	}

	public String generateInvoiceForDealer(
			OrderInfoObjectForDealer orderInfoObjectForDealer) throws Exception {
		return generateInvoiceForDealer(new OrderForDealerInvoice(
				orderInfoObjectForDealer));
	}

	private String generateInvoiceForDealer(OrderForDealerInvoice order)
			throws Exception {
		this.order = order;
		document = new PDDocument();

		newPage();
		addTopMargin();
		addImage();
		addInvoiceHeader();
		addInvoiceMetaData();

		addBuyerSellerBlock();
		// addDispatchInfo();
		addRow(Arrays.asList(" ", " "));
		addItemTable();

		addTotalMetaData();

		// addDeclaration("Declaration:This is to declare....");
		// addDeclaration("Declaration           ,This is to declare....");

		File file = new File("/home/mowgli/DealerInvoice.pdf");

		// File file = new File("DealerInvoice.pdf");

		document.save(file);
		document.close();

		java.io.FileInputStream fileInputStream = new java.io.FileInputStream(
				file);

		long contentLength = file.getTotalSpace();

		String locationName = "user/order/" + order.getUserId() + "/"
				+ order.getOrderId() + "/" + file.getName();

		String url = filestorage.uploadFile(AzureFileStorage.FILE_CONTAINER,
				locationName, fileInputStream, contentLength);

		// return file.getAbsolutePath();
		return url;
	}
}
