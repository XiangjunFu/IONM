package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.UserService;
import com.wanma.domain.UserOdn;
import com.wanma.server.dao.UserDao;
import com.wanma.server.dao.impl.UserDaoImpl;
import com.wanma.server.utils.HibernateUtil;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4443950671324172281L;

	private UserDao userDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userDao = new UserDaoImpl();
	}

	@Override
	public String addUser(UserOdn user) {
		return userDao.addUser(user);
	}

	@Override
	public List<UserOdn> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public boolean updateUserPassword(String userName, String password) {
		return userDao.updateUserPassword(userName, password);
	}

	@Override
	public UserOdn getUserByUserName(String username) {
		return userDao.getUserByUserName(username);
	}
	
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

	@Override
	public boolean deleteUserByUserName(String username) {
		return userDao.deleteUserByUserName(username);
	}
	
	public String print(){
		return "callback";
	}
}
