package com.sarvah.mbg.promotion.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.common.asset.Video;


public interface DashBoardVideoDAO extends MongoRepository<Video, String>{

}
