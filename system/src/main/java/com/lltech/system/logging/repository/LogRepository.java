package com.lltech.system.logging.repository;

import com.lltech.system.logging.model.LogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author CREATED BY L.C.Y on 2019-4-1
 */
@Repository
public interface LogRepository extends JpaRepository<LogDO, Long>, JpaSpecificationExecutor {

    /**
     * 获取一个时间段的IP记录
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select count(*) FROM (select request_ip FROM sys_log where create_time between ?1 and ?2 GROUP BY request_ip) as s",nativeQuery = true)
    Long findIp(String startDate, String endDate);
}
