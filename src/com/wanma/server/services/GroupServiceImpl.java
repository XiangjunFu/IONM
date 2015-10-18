package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
import com.wanma.server.dao.impl.GroupDaoImpl;
import com.wanma.server.utils.HibernateUtil;

public class GroupServiceImpl extends RemoteServiceServlet implements
		GroupService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 677089317909118026L;

	private GroupDao groupDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		groupDao = new GroupDaoImpl();
	}

	@Override
	public WorkGroupId addGroup(WorkGroup group) {
		HttpSession httpSession = this.getThreadLocalRequest().getSession();
		String loginName = (String)httpSession.getAttribute("loginName");
		if(loginName != null && !"".equals(loginName)){
			group.getId().setOwnerName(loginName);
		}else{
			return null;
		}
		return groupDao.addGroup(group);
	}

	@Override
	public List<WorkGroup> getWorkGroupIds() {
		return groupDao.getWorkGroupIds();
	}

	@Override
	public List<UserOdn> getUsersByGroup(String groupName) {
		return groupDao.getUsersByGroup(groupName);
	}
	
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
		return groupDao.deleteGroup(name);
	}
}
