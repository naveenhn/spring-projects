/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import java.util.List;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.common.asset.Video;

/**
 * @author naveen
 *
 */
public class ProductAsset {
	
	
	private List<Image> images;
	private Video video;
	private List<Image> images3D;
	private List<File> relatedFiles;
	
	/**
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(List<Image> images) {
		this.images = images;
	}
	/**
	 * @return the video
	 */
	public Video getVideo() {
		return video;
	}
	/**
	 * @param video the video to set
	 */
	public void setVideo(Video video) {
		this.video = video;
	}
	/**
	 * @return the images3D
	 */
	public List<Image> getImages3D() {
		return images3D;
	}
	/**
	 * @param images3d the images3D to set
	 */
	public void setImages3D(List<Image> images3d) {
		images3D = images3d;
	}
	/**
	 * @return the relatedFiles
	 */
	public List<File> getRelatedFiles() {
		return relatedFiles;
	}
	/**
	 * @param relatedFiles the relatedFiles to set
	 */
	public void setRelatedFiles(List<File> relatedFiles) {
		this.relatedFiles = relatedFiles;
	}
	
 
}
