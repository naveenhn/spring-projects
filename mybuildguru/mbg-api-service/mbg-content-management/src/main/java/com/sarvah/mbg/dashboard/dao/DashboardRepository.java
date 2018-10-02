/**
 * 
 */
package com.sarvah.mbg.dashboard.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.dashboard.Dashboard;

/**
 * @author naveen
 *
 */
public interface DashboardRepository extends CrudRepository<Dashboard, Integer> {

	List<Dashboard> findByDashBoardType_DescriptionLike(String string);
	List<Dashboard> findByDashBoardType_DashboardTypeId(int dashBoardTypeId);
	


}
