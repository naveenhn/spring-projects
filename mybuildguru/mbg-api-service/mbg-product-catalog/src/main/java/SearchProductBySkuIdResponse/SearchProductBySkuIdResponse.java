/**
 * 
 */
package SearchProductBySkuIdResponse;

import java.util.Set;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;

/**
 * @author shivu s
 *
 */
public class SearchProductBySkuIdResponse {
	private String productId;
	private String skuId;
	private String name;
	private String model;
	private String longName;
	private Description desc;
	private ProductBrand brand;
	private ProductQuantityType quantityType;
	private Set<SubCategory> subcategories;
	private Set<ProductType> productTypes;
	private ProductAsset assets;
	private ProductAttribute features;
	private Set<ProductAttribute> attributes;
	private Set<ProductFaq> faqs;
	private File tcDoc;
	private Price price;
	private boolean accessory;
	private Set<String> relatedProducts;
	private Set<String> accessories;
	private ProductStatus status;
	private ConsolidatedRating consolidatedRating;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public Description getDesc() {
		return desc;
	}

	public void setDesc(Description desc) {
		this.desc = desc;
	}

	public ProductBrand getBrand() {
		return brand;
	}

	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	public ProductQuantityType getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(ProductQuantityType quantityType) {
		this.quantityType = quantityType;
	}

	public Set<SubCategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(Set<SubCategory> subcategories) {
		this.subcategories = subcategories;
	}

	public Set<ProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(Set<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

	public ProductAsset getAssets() {
		return assets;
	}

	public void setAssets(ProductAsset assets) {
		this.assets = assets;
	}

	public ProductAttribute getFeatures() {
		return features;
	}

	public void setFeatures(ProductAttribute features) {
		this.features = features;
	}

	public Set<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

	public Set<ProductFaq> getFaqs() {
		return faqs;
	}

	public void setFaqs(Set<ProductFaq> faqs) {
		this.faqs = faqs;
	}

	public File getTcDoc() {
		return tcDoc;
	}

	public void setTcDoc(File tcDoc) {
		this.tcDoc = tcDoc;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public boolean isAccessory() {
		return accessory;
	}

	public void setAccessory(boolean accessory) {
		this.accessory = accessory;
	}

	public Set<String> getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(Set<String> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	public Set<String> getAccessories() {
		return accessories;
	}

	public void setAccessories(Set<String> accessories) {
		this.accessories = accessories;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public ConsolidatedRating getConsolidatedRating() {
		return consolidatedRating;
	}

	public void setConsolidatedRating(ConsolidatedRating consolidatedRating) {
		this.consolidatedRating = consolidatedRating;
	}

}
