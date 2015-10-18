package com.wanma.server.dao;

import java.util.List;

import org.junit.Test;

import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;
import com.wanma.domain.WorkGroupId;

public interface GroupDao {

	public abstract WorkGroupId addGroup(WorkGroup group);

	public abstract List<WorkGroup> getWorkGroupIds();

	public abstract List<UserOdn> getUsersByGroup(String groupName);

	@Test
	public abstract void testrgad();

	public abstract boolean deleteGroup(String name);

}