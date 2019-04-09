package com.lltech.system.quartz.service.query;

import com.lltech.system.quartz.model.QuartzJob;
import com.lltech.system.quartz.repository.QuartzJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobQueryService {

    private final QuartzJobRepository quartzJobRepository;

    @Autowired
    public QuartzJobQueryService(QuartzJobRepository quartzJobRepository) {
        this.quartzJobRepository = quartzJobRepository;
    }

    public Page queryAll(QuartzJob quartzJob, Pageable pageable){
        return quartzJobRepository.findAll(new Spec(quartzJob),pageable);
    }

    class Spec implements Specification<QuartzJob> {

        private QuartzJob quartzJob;

        Spec(QuartzJob quartzJob){
            this.quartzJob = quartzJob;
        }

        @Override
        public Predicate toPredicate(Root<QuartzJob> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            if(!ObjectUtils.isEmpty(quartzJob.getJobName())){

                // 模糊
                list.add(cb.like(root.get("jobName").as(String.class),"%"+quartzJob.getJobName()+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
