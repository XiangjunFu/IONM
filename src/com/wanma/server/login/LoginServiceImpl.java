package com.wanma.server.login;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.login.LoginService;
import com.wanma.domain.UserOdn;
import com.wanma.server.utils.HibernateUtil;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258519102104559219L;

	@Override
	public boolean checkLogin(String username, String password) {
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		UserOdn user = (UserOdn) session.get(UserOdn.class, username);
		if(user == null){
			return false;
		}
		HttpSession httpsession = getThreadLocalRequest().getSession();
		httpsession.setAttribute("loginName", user.getUserName());
		tx.commit();
		return user.getPassword().equals(password);
	}

	@Override
	public UserOdn getUser(String username) {
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		UserOdn user = (UserOdn) criteria.list().get(0);
		HttpSession httpsession = getThreadLocalRequest().getSession();
		httpsession.setAttribute("loginName", user.getUserName());
		tx.commit();
		//return user.getPassword().equals(password);
		return user;
	}

	@Override
	public String test(String name) {
		//查询数据库，获取用户信息
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(UserOdn.class);
		criteria.add(Restrictions.eq("userName", name));
		UserOdn user = (UserOdn) criteria.list().get(0);
		tx.commit();
		//return user.getPassword().equals(password);
		return user.getPassword();
	}
	
}
