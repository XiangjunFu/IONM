package com.wanma.client.utils;

public class Constants {

	//每个机框所能含有的板卡数目
	public static final int boardNumPerFrame = 12;
	public static final int portNumPerBoard = 12;
	public static final int rowNumPerBoard = 1;
	//设施状态
	public static String[] deviceStatus = {"已用","设计","在建","竣工","废弃","其他"};
	
	//分光器级别
	public static String[] splitLevel = {"一级","二级","三级","四级","五级","六级及以上"};
	
	//所在设备类型
	public static String[] deviceType = {"光纤交接箱(IFDT)","光纤配线架(IODF)","光分纤盒(IFAT)"};
	
	//配置工单状态，1，2，3，4，5，6，7
	public static String[] ConfigWorkListStatus = {"","预配置","已指派待施工","施工中","完工待同步","完工已同步","拒绝待退单","已取消"};
	
	//业务工单状态，1，2，3，4，5，6，7
	public static String[] BusinessWorkListStatus = {"","创建未建立路由","创建已建立路由","指派未完工","指派已完工","指派要求改纤","退单","撤销"};
	
	//路由工单状态，1，2，3，4，5，6，7
	public static String[] routeWorkListStatus = {"","未指派","指派待施工","施工中","完工待同步","完工已同步","拒绝待退单","已取消"};
	//巡检工单状态，1，2，3，4，5，6，7
	public static String[] checkWorkListStatus = {"","未指派","指派待施工","施工中","完工待同步","完工已同步","拒绝待退单","已取消"};
	
	public static String[] boardWorkListStatus = {"","未指派","指派待施工","施工中","完工待同步","完工已同步","拒绝待退单","已取消"};
	
	public static String[] frameWorkListStatus = {"","未指派","指派待施工","施工中","完工待同步","完工已同步","拒绝待退单","已取消"};
	
	public static String[] boardOpType = {"","加载单板","拆除单板"}; 
	
	public static String[] frameOpType = {"","加载子框","拆除子框"};
	
	public static String[] boardType = {"","一体化盘","1分8","1分16","1分32","1分64"};
	
	public static String[] boardStatus = {"","未管理","已管理","已删除"};
	
	public static String[] frameStatus = {"","未管理","已管理","已删除"};
	
	public static String[] portStatus = {"","空闲","占用","有跳纤插入","有跳纤拔出","预跳纤"};// 1 2 3 4 5
	
	public static String[] serviceStatus = {"","空闲","占用","有跳纤插入","有跳纤拔出","已删除"};
	
	public static String[] alarmType = {"","恢复正常","有跳纤插入","有跳纤拔出"};
	
}
