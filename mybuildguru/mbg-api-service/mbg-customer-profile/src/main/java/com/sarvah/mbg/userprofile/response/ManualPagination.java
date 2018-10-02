/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import org.springframework.stereotype.Service;

/**
 * @author shivu s
 *
 */
@Service
public class ManualPagination {
	public int getPageElementStartNumber(int page, int size) {
		int pageElementStartNumber;
		if (page == 0) {
			pageElementStartNumber = 0;
		} else {
			pageElementStartNumber = page * size;
		}
		return pageElementStartNumber;
	}

	public int getPageElementEndNumber(int page, int size, int totalElement,
			int totalPages) throws Exception {
		int pageElementEndNumber;
		pageElementEndNumber = page * size + size;
		if (pageElementEndNumber <= totalElement) {
			return pageElementEndNumber;
		} else if (pageElementEndNumber <= totalElement + size) {
			return totalElement;
		} else {
			throw new Exception(
					"page size out of range!!! paze range should be 0 to "
							+ (totalPages - 1));
		}
	}
}
