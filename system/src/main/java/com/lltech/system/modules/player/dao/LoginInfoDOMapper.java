package com.lltech.system.modules.player.dao;

import com.lltech.system.modules.player.model.LoginInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginInfoDOMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LoginInfoDO record);

    LoginInfoDO selectByPrimaryKey(Long id);

    /**
     * 登录记录列表
     * @param playerId 玩家id
     * @return 列表
     */
    List<LoginInfoDO> listByPlayerId(Long playerId);

    int updateByPrimaryKeySelective(LoginInfoDO record);

}
