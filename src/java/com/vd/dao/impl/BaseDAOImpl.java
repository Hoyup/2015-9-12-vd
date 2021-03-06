package com.vd.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vd.dao.BaseDAO;
import com.vd.util.Page;

/**
 * 数据库操作基础类
 * 
 * @param <T>
 */

@Repository
public abstract class BaseDAOImpl<T> implements BaseDAO<T>{
	protected SessionFactory sessionFactory;

	@Autowired
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存
	 * 
	 * @param t
	 */
	public Long save(T t) {
		return (Long) getSession().save(t);
	}

	/**
	 * 更新
	 * 
	 * @param t
	 */
	public void update(T t) {
		getSession().update(t);
	}

	/**
	 * 添加或更新
	 * 
	 * @param t
	 */
	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}

	/**
	 * 获得id对象
	 * 
	 * @param id
	 * @return
	 */
	public T get(Long id) {
		return get(id, false);
	}

	/**
	 * 通过id删除对象
	 * 
	 * @param id
	 */
	public void del(Long id) {
		T t = get(id);
		if (t != null) {
			getSession().delete(t);
		}
	}

	public void del(T t) {
		if (t != null) {
			getSession().delete(t);
		}
	}

	@SuppressWarnings("unchecked")
	protected T get(Long id, boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().get(getEntityClass(), id, LockMode.UPGRADE);
		} else {
			entity = (T) getSession().get(getEntityClass(), id);
		}
		return entity;
	}

	public List<T> getObjectList(Map<String, Object> eqParams) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		if (eqParams != null) {
			if (!eqParams.isEmpty()) {
				Set<String> keys = eqParams.keySet();
				for (String paramKey : keys) {
					Object obj = eqParams.get(paramKey);
					if (obj instanceof Collection<?>) {
						criteria.add(Restrictions.in(paramKey, (Collection<?>)obj));
					} else if (obj instanceof Object[]) {
						criteria.add(Restrictions.in(paramKey, (Object[]) obj));
					} else {
						criteria.add(Restrictions.eq(paramKey, obj));
					}
				}
			}
		}
		return criteria.list();
	}
	
	public T getObject(Map<String, Object> eqParams) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		if (eqParams != null) {
			if (!eqParams.isEmpty()) {
				Set<String> keys = eqParams.keySet();
				for (String paramKey : keys) {
					Object paramVal = eqParams.get(paramKey);
					criteria.add(Restrictions.eq(paramKey, paramVal));
				}
			}
		}
		criteria.setMaxResults(1);
		return (T) criteria.uniqueResult();
	}

	abstract protected Class<T> getEntityClass();

	public List<T> getByPrams(Map<String, Object> prams, Map<String, Object> sortPram,
			Map<String, Object> searchPram){
		Criteria criteria = getSession().createCriteria(getEntityClass());
		//设置参数

		if (prams != null) {
			if (!prams.isEmpty()) {
				Set<String> keys = prams.keySet();
				for (String paramKey : keys) {
					Object paramVal = prams.get(paramKey);
					criteria.add(Restrictions.eq(paramKey, paramVal));
				}
			}
		}
		//模糊查询，最多支持两个字段模糊查询

		if (searchPram != null) {
			if (!searchPram.isEmpty()) {
				Set<String> keys = searchPram.keySet();
				if(keys.size() == 1){
					String paramKey = keys.toArray()[0].toString();
					String paramVal = searchPram.get(paramKey).toString();
					criteria.add(Restrictions.like(paramKey, paramVal, MatchMode.ANYWHERE));
				}else if(keys.size() == 2){
					String paramKey_1 = keys.toArray()[0].toString();
					String paramVal_1 = searchPram.get(paramKey_1).toString();
					String paramKey_2 = keys.toArray()[1].toString();
					String paramVal_2 = searchPram.get(paramKey_2).toString();
					criteria.add(Restrictions.or(Restrictions.like(paramKey_1, paramVal_1, MatchMode.ANYWHERE), Restrictions.like(paramKey_2, paramVal_2, MatchMode.ANYWHERE)));
				}
			}
		}
		//设置排序

		if (sortPram != null) {
			if (!sortPram.isEmpty()) {
				Set<String> keys = sortPram.keySet();
				for (String paramKey : keys) {
					Object paramVal = sortPram.get(paramKey);
					if(paramKey.equals("desc")){
						criteria.addOrder(Order.desc(paramVal.toString()));
					}else if(paramKey.equals("asc")){
						criteria.addOrder(Order.asc(paramVal.toString()));
					}
				}
			}
		}
		return criteria.list();
	}
	
	/**
	 * 按条件查询,排序,分页
	 */
	public Page getByPrams(Map<String, Object> prams, Map<String, Object> sortPram, Map<String, Object> searchPram, Integer iDisplayStart, Integer iDisplayLength){
		Criteria criteria = getSession().createCriteria(getEntityClass());
		//设置参数

		if (prams != null) {
			if (!prams.isEmpty()) {
				Set<String> keys = prams.keySet();
				for (String paramKey : keys) {
					Object paramVal = prams.get(paramKey);
					criteria.add(Restrictions.eq(paramKey, paramVal));
				}
			}
		}
		//模糊查询，最多支持两个字段模糊查询

		if (searchPram != null) {
			if (!searchPram.isEmpty()) {
				Set<String> keys = searchPram.keySet();
				if(keys.size() == 1){
					String paramKey = keys.toArray()[0].toString();
					String paramVal = searchPram.get(paramKey).toString();
					criteria.add(Restrictions.like(paramKey, paramVal, MatchMode.ANYWHERE));
				}else if(keys.size() == 2){
					String paramKey_1 = keys.toArray()[0].toString();
					String paramVal_1 = searchPram.get(paramKey_1).toString();
					String paramKey_2 = keys.toArray()[1].toString();
					String paramVal_2 = searchPram.get(paramKey_2).toString();
					criteria.add(Restrictions.or(Restrictions.like(paramKey_1, paramVal_1, MatchMode.ANYWHERE), Restrictions.like(paramKey_2, paramVal_2, MatchMode.ANYWHERE)));
				}
			}
		}
		//设置排序

		if (sortPram != null) {
			if (!sortPram.isEmpty()) {
				Set<String> keys = sortPram.keySet();
				for (String paramKey : keys) {
					Object paramVal = sortPram.get(paramKey);
					if(paramKey.equals("desc")){
						criteria.addOrder(Order.desc(paramVal.toString()));
					}else if(paramKey.equals("asc")){
						criteria.addOrder(Order.asc(paramVal.toString()));
					}
				}
			}
		}
		//设置页码

		criteria.setFirstResult(iDisplayStart);
		criteria.setMaxResults(iDisplayLength);
		Page page = new Page();
		page.setList(criteria.list());
		page.setTotalCount(getByPrams(prams, searchPram));
		return page;
	}
	
	/**
	 * 按条件查询总数
	 */
	public int getByPrams(Map<String, Object> prams, Map<String, Object> searchPram){
		Criteria criteria = getSession().createCriteria(getEntityClass());
		//设置参数

		if (prams != null) {
			if (!prams.isEmpty()) {
				Set<String> keys = prams.keySet();
				for (String paramKey : keys) {
					Object paramVal = prams.get(paramKey);
					criteria.add(Restrictions.eq(paramKey, paramVal));
				}
			}
		}
		//模糊查询，最多支持两个字段模糊查询

		if (searchPram != null) {
			if (!searchPram.isEmpty()) {
				Set<String> keys = searchPram.keySet();
				if(keys.size() == 1){
					String paramKey = keys.toArray()[0].toString();
					String paramVal = searchPram.get(paramKey).toString();
					criteria.add(Restrictions.like(paramKey, paramVal, MatchMode.ANYWHERE));
				}else if(keys.size() == 2){
					String paramKey_1 = keys.toArray()[0].toString();
					String paramVal_1 = searchPram.get(paramKey_1).toString();
					String paramKey_2 = keys.toArray()[1].toString();
					String paramVal_2 = searchPram.get(paramKey_2).toString();
					criteria.add(Restrictions.or(Restrictions.like(paramKey_1, paramVal_1, MatchMode.ANYWHERE), Restrictions.like(paramKey_2, paramVal_2, MatchMode.ANYWHERE)));
				}
			}
		}
		return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
	
	/**
	 * 根据HQL查询
	 */
	public List<T> getByHQL(String hql, Map<String, Object> prams) {
		Query query = getSession().createQuery(hql);
		// 匹配参数

		if (prams != null) {
			Set<String> keys = prams.keySet();
			for (String paramKey : keys) {
				Object obj = prams.get(paramKey);
				if (obj instanceof Collection<?>) {
					query.setParameterList(paramKey, (Collection<?>)obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(paramKey, (Object[]) obj);
				} else {
					query.setParameter(paramKey, obj);
				}
			}
		}
//		query.setCacheable(true);

		return query.list();
	}
	
	public void updateByHQL(String hql, Map<String, Object> prams) {
		Query query = getSession().createQuery(hql);
		// 匹配参数

		if (prams != null) {
			Set<String> keys = prams.keySet();
			for (String paramKey : keys) {
				Object paramVal = prams.get(paramKey);
				query.setParameter(paramKey, paramVal);
			}
		}
		query.executeUpdate();
	}
	
	public List<Map<String, Object>> getBySQL(String sql, Map<String, Object> prams, Integer star, Integer length) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		// 匹配参数

		if (prams != null) {
			Set<String> keys = prams.keySet();
			for (String paramKey : keys) {
				Object paramVal = prams.get(paramKey);
				query.setParameter(paramKey, paramVal);
			}
		}
		query.setFirstResult(star);
		query.setMaxResults(length);
		return query.list();
	}
	
	public List<Map<String, Object>> getBySQL(String sql, Map<String, Object> prams) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		// 匹配参数

		if (prams != null) {
			Set<String> keys = prams.keySet();
			for (String paramKey : keys) {
				Object obj = prams.get(paramKey);
				if (obj instanceof Collection<?>) {
					query.setParameterList(paramKey, (Collection<?>)obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(paramKey, (Object[]) obj);
				} else {
					query.setParameter(paramKey, obj);
				}
			}
		}
		return query.list();
	}
}