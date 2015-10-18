package com.wanma.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.wanma.domain.UserOdn;
import com.wanma.server.dao.UserDao;
import com.wanma.server.utils.HibernateUtil;

public class UserDaoImpl implements UserDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4443950671324172281L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.UserDao#addUser(com.wanma.domain.UserOdn)
	 */
	@Override
	public String addUser(UserOdn user) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(user);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.UserDao#getAllUsers()
	 */
	@Override
	public List<UserOdn> getAllUsers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		List<UserOdn> temp = (List<UserOdn>)criteria.list();
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.UserDao#updateUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUserPassword(String userName, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update UserOdn set password='" + password + "' where userName='" + userName + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.UserDao#getUserByUserName(java.lang.String)
	 */
	@Override
	public UserOdn getUserByUserName(String username) {
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		criteria.add(Restrictions.eq("userName", username));
		List<UserOdn> users = (List<UserOdn>) criteria.list();
		tx.commit();
		return users.size() >= 1 ? users.get(0) : null;
	}
	
	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.UserDao#testgetUserdv()
	 */
	@Test
	public void testgetUserdv(){
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		criteria.add(Restrictions.eq("userName", "test"));
		List<UserOdn> users = (List<UserOdn>) criteria.list();
		tx.commit();
		System.out.println(users.size());
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.UserDao#deleteUserByUserName(java.lang.String)
	 */
	@Override
	public boolean deleteUserByUserName(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from UserOdn where userName='" + username +"'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean checklogin(String username, String password) {
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		UserOdn user = (UserOdn) session.get(UserOdn.class, username);
		if(user == null){
			return false;
		}
		tx.commit();
		return user.getPassword().equals(password);
	}

	@Override
	public List<UserOdn> getUsersByGroup(String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		criteria.add(Restrictions.eq("groupName", name));
		List<UserOdn> users = (List<UserOdn>) criteria.list();
		tx.commit();
		return users;
	}
}
