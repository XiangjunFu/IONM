package com.wanma.server.dao.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.GroupService;
import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;
import com.wanma.domain.WorkGroupId;
import com.wanma.server.dao.GroupDao;
import com.wanma.server.utils.HibernateUtil;

public class GroupDaoImpl implements GroupDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 677089317909118026L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.GroupDao#addGroup(com.wanma.domain.WorkGroup)
	 */
	@Override
	public WorkGroupId addGroup(WorkGroup group) {
		WorkGroupId temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (WorkGroupId) session.save(group);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.GroupDao#getWorkGroupIds()
	 */
	@Override
	public List<WorkGroup> getWorkGroupIds() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(WorkGroup.class);
		List<WorkGroup> WorkGroups = criteria.list();
		tx.commit();
		return WorkGroups;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.GroupDao#getUsersByGroup(java.lang.String)
	 */
	@Override
	public List<UserOdn> getUsersByGroup(String groupName) {
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		criteria.add(Restrictions.eq("groupName", groupName));
		List<UserOdn> users = (List<UserOdn>) criteria.list();
		//HttpSession httpsession = getThreadLocalRequest().getSession();
		//httpsession.setAttribute("loginName", user.getUserName());
		tx.commit();
		//return user.getPassword().equals(password);
		return users;
	}
	
	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.GroupDao#testrgad()
	 */
	@Override
	@Test
	public void testrgad(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		criteria.add(Restrictions.eq("groupName", "group01"));
		List<UserOdn> users = (List<UserOdn>) criteria.list();
		//HttpSession httpsession = getThreadLocalRequest().getSession();
		//httpsession.setAttribute("loginName", user.getUserName());
		tx.commit();
		
		System.out.println(users);
	}

	@Override
	public boolean deleteGroup(String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		return false;
	}
}
