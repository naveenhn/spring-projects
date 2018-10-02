/**
 * 
 */
package com.sarvah.mbg.domain.common.asset;


/**
 * @author naveen
 *
 */
public class Image extends File{	
	
		
		
		private int height;
		private int width;
		
		
	protected Image() {
		// default constructor
	}
		/**
		 * Constructor
		 * @param name
		 * @param url
		 */
		public Image(String name, String url) {
			super(name, url);
			
		}
		
		/**
		 * Constructor
		 * @param name
		 * @param url
		 * @param height
		 * @param width
		 */
		public Image(String name, String url, int height, int width) {
			
			this(name, url);
			this.height = height;
			this.width = width;
		}
		
		
		/**
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}
		/**
		 * @param height the height to set
		 */
		public void setHeight(int height) {
			this.height = height;
		}
		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}
		/**
		 * @param width the width to set
		 */
		public void setWidth(int width) {
			this.width = width;
		}
		

}
