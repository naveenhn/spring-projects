/**
 * 
 */
package com.sarvah.mbg.batch.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;

/**
 * @author Shiva
 *
 */
public interface UserOrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByCreateBy(String createby);

	List<Order> findAll();
}
