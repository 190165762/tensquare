package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhua
 * @date 2019/9/3 21:51
 */
@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        /**
         * root 根对象
         * query 封装的查询关键字
         * cb 用来封装对象的 如果直接返回null 表示不需要任何条件
         */
        return labelDao.findAll((Specification<Label>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (label.getLabelname() != null && "".equals(label.getLabelname())) {
                Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");// where labelname like '%%'
                predicateList.add(predicate);
            }
            if (label.getState() != null && "".equals(label.getState())) {
                Predicate predicate = cb.equal(root.get("state").as(Integer.class), label.getState());// where labelname like '%%'
                predicateList.add(predicate);
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            return cb.and(predicates);
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll((Specification<Label>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (label.getLabelname() != null && "".equals(label.getLabelname())) {
                Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");// where labelname like '%%'
                predicateList.add(predicate);
            }
            if (label.getState() != null && "".equals(label.getState())) {
                Predicate predicate = cb.equal(root.get("state").as(Integer.class), label.getState());// where labelname like '%%'
                predicateList.add(predicate);
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            return cb.and(predicates);
        }, pageable);
    }
}
