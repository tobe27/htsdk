package com.lltech.system.quartz.service.query;

import com.lltech.system.quartz.model.QuartzLog;
import com.lltech.system.quartz.repository.QuartzLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2019-01-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzLogQueryService {

    private final QuartzLogRepository quartzLogRepository;

    @Autowired
    public QuartzLogQueryService(QuartzLogRepository quartzLogRepository) {
        this.quartzLogRepository = quartzLogRepository;
    }

    public Page queryAll(QuartzLog quartzLog, Pageable pageable){
        return quartzLogRepository.findAll(new Spec(quartzLog),pageable);
    }

    class Spec implements Specification<QuartzLog> {

        private QuartzLog quartzLog;

        Spec(QuartzLog quartzLog){
            this.quartzLog = quartzLog;
        }

        @Override
        public Predicate toPredicate(Root<QuartzLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            if(!ObjectUtils.isEmpty(quartzLog.getJobName())){


                // 模糊查询
                list.add(cb.like(root.get("jobName").as(String.class),"%"+quartzLog.getJobName()+"%"));
            }

            if (!ObjectUtils.isEmpty(quartzLog.getIsSuccess())) {
                list.add(cb.equal(root.get("isSuccess").as(Boolean.class), quartzLog.getIsSuccess()));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
