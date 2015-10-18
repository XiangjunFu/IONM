package com.wanma.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.wanma.client.utils.Constants;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Frame;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.JumperConnector;
import com.wanma.domain.Obd;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.utils.HibernateUtil;

public class OpticalDeviceDaoImpl implements OpticalDeviceDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6093960664389241798L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#addIFDT(com.wanma.domain.Ifdt)
	 */
	@Override
	public Integer addIFDT(Ifdt ifdt) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(ifdt);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getIfdts()
	 */
	@Override
	public List<Ifdt> getIfdts() {
		List<Ifdt> ifdts = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Ifdt.class);
		ifdts = criteria.list();
		tx.commit();
		return ifdts;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#addOBD(com.wanma.domain.Obd)
	 */
	@Override
	public Integer addOBD(Obd obd) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(obd);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#addIODF(com.wanma.domain.Iodf)
	 */
	@Override
	public Integer addIODF(Iodf iodf) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(iodf);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#addIFAT(com.wanma.domain.Ifat)
	 */
	@Override
	public Integer addIFAT(Ifat ifat) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(ifat);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getBoards(java.lang.String)
	 */
	@Override
	public List<TerminalUnit> getBoards(String deviceName) {
		List<TerminalUnit> temp = new ArrayList<TerminalUnit>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		criteria.add(Restrictions.eq("deviceName", deviceName));
		DeviceCommonInfo deviceCommonInfo = (DeviceCommonInfo)criteria.list().get(0);
		String deviceCode = deviceCommonInfo.getDeviceCode();
		
		Criteria criteria2 = session.createCriteria(TerminalUnit.class);
		criteria2.add(Restrictions.eq("hostDeviceId", deviceCode));
		temp = criteria2.list();
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getAllDeviceInfo()
	 */
	@Override
	public List<DeviceCommonInfo> getAllDeviceInfo() {
		List<DeviceCommonInfo> temp = new ArrayList<DeviceCommonInfo>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		temp = criteria.list();
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getObds()
	 */
	@Override
	public List<Obd> getObds() {
		List<Obd> obds = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Obd.class);
		obds = criteria.list();
		tx.commit();
		return obds;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getIodfs()
	 */
	@Override
	public List<Iodf> getIodfs() {
		List<Iodf> iodfs = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Iodf.class);
		iodfs = criteria.list();
		tx.commit();
		return iodfs;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getIfats()
	 */
	@Override
	public List<Ifat> getIfats() {
		List<Ifat> ifats = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Ifat.class);
		ifats = criteria.list();
		tx.commit();
		return ifats;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getIfdtByCode(java.lang.String)
	 */
	@Override
	public Ifdt getIfdtByCode(String paramDeviceCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Ifdt.class);
		criteria.add(Restrictions.eq("code", paramDeviceCode));
		Ifdt ifdt = (Ifdt)criteria.list().get(0);
		tx.commit();
		return ifdt;
	}
	
	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getObdInfoByCode(java.lang.String)
	 */
	@Override
	public Obd getObdInfoByCode(String paramDeviceCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Obd.class);
		criteria.add(Restrictions.eq("code", paramDeviceCode));
		Obd obd = (Obd)criteria.list().get(0);
		tx.commit();
		return obd;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getIodfInfoByCode(java.lang.String)
	 */
	@Override
	public Iodf getIodfInfoByCode(String paramDeviceCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Iodf.class);
		criteria.add(Restrictions.eq("code", paramDeviceCode));
		Iodf iodf = (Iodf)criteria.list().get(0);
		tx.commit();
		return iodf;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.OpticalDeviceDao#getIfatInfoByCode(java.lang.String)
	 */
	@Override
	public Ifat getIfatInfoByCode(String paramDeviceCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Ifat.class);
		criteria.add(Restrictions.eq("code", paramDeviceCode));
		Ifat ifat = (Ifat)criteria.list().get(0);
		tx.commit();
		return ifat;
	}

	@Override
	public List<DeviceCommonInfo> getDeviceInfosByHostRoomName(String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		criteria.add(Restrictions.eq("hostRoomName", name));
		List<DeviceCommonInfo> deviceCommonInfos = criteria.list();
		tx.commit();
		return deviceCommonInfos;
	}

	@Override
	public List<Port> getPortsByDeviceCode(String deviceCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Port.class);
		criteria.add(Restrictions.eq("meCode", deviceCode));
		List<Port> ports = criteria.list();
		tx.commit();
		return ports;
	}

	@Override
	public void addDeviceCommonInfo(DeviceCommonInfo deviceCommonInfo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(deviceCommonInfo);
		tx.commit();
	}

	@Override
	public List<DeviceCommonInfo> getDeviceInfosByType(String type) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		criteria.add(Restrictions.eq("deviceType", type));
		List<DeviceCommonInfo> deviceCommonInfos = criteria.list();
		tx.commit();
		return deviceCommonInfos;
	}

	@Override
	public Integer savePort(Port port) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(port);
		tx.commit();
		return temp;
	}

	@Override
	public List<Port> getAllPorts() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Port.class);
		List<Port> ports = criteria.list();
		tx.commit();
		return ports;
	}

	//通过设备名获取设备编码，然后获取端口
	@Override
	public List<Port> getPortsByDeviceName(String deviceName) {
		DeviceCommonInfo deviceCommonInfo = getDeviceCommonInfoByDeviceName(deviceName);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Port.class);
		criteria.add(Restrictions.eq("meCode", deviceCommonInfo.getDeviceCode()));
		List<Port> ports = criteria.list();
		tx.commit();
		return ports;
	}

	@Override
	public List<TerminalUnit> getBoardsByHostDeviceID(String hostDeviceID,String status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(TerminalUnit.class);
		if(!"".equals(status)){
			criteria.add(Restrictions.eq("status", status));
		}
		if(!"".equals(hostDeviceID)){
			criteria.add(Restrictions.eq("hostDeviceId", hostDeviceID));
		}
		List<TerminalUnit> terminalUnits = criteria.list();
		tx.commit();
		return terminalUnits;
	}

	/**
	 * 获取端口信息
	 * 有板卡编码，则查询相应板卡上的端口
	 * 有状态，则查询相应状态的端口
	 */
	@Override
	public List<Port> getPortsByBoardCode(String boardCode,String status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Port.class);
		//状态
		if(status != null && !"".equals(status)){
			criteria.add(Restrictions.eq("serviceStatue", status));
		}
		//板卡
		if(boardCode != null && !"".equals(boardCode)){
			criteria.add(Restrictions.eq("parentCode", boardCode));
		}
		List<Port> ports = criteria.list();
		tx.commit();
		return ports;
	}

	@Test
	public void testGetPorts(){
		String boardCode = "ifdt01/1/1"; 
		String status = "";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Port.class);
		//状态
		if(status != null && !"".equals(status)){
			criteria.add(Restrictions.eq("physicalStatue", status));
		}
		//板卡
		if(boardCode != null && !"".equals(boardCode)){
			criteria.add(Restrictions.eq("parentCode", boardCode));
		}
		List<Port> ports = criteria.list();
		tx.commit();
		System.out.println(ports.size());
	}
	
	/**
	 * 通过 板卡编码查询板卡
	 */
	@Override
	public TerminalUnit getBoardByBoardCode(String boardCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(TerminalUnit.class);
		criteria.add(Restrictions.eq("terminalUnitCode", boardCode));
		TerminalUnit terminalUnit = (TerminalUnit)criteria.list().get(0);
		tx.commit();
		return terminalUnit;
	}

	@Override
	public DeviceCommonInfo getDeviceCommonInfoByDeviceCode(String deviceCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		criteria.add(Restrictions.eq("deviceCode", deviceCode));
		DeviceCommonInfo deviceCommonInfo = (DeviceCommonInfo)criteria.list().get(0);
		tx.commit();
		return deviceCommonInfo;
	}

	@Override
	public Integer addBoard(TerminalUnit terminalUnit) {
		
		Integer id = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		id = (Integer) session.save(terminalUnit);
		tx.commit();
		if(id != -1){
			int boardNum = Integer.parseInt(terminalUnit.getTerminalUnitSeq());
			int temp = boardNum / Constants.boardNumPerFrame;
			int temp0 = boardNum % Constants.boardNumPerFrame;
			int frameNum = temp0 == 0? temp:temp + 1;
			for(int k = 1;k<=12;k++){
				addPortInfo(terminalUnit.getHostDeviceId(),frameNum,boardNum,k);
			}
		}
		return id;
	}

	@Override
	public Integer addFrame(Frame frame) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(frame);
		tx.commit();
		return temp;
	}

	@Override
	public Integer deleteBoardInfo(String boardCode) {
		String status = "3";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update TerminalUnit set status='" + status + "' where terminalUnitCode='" + boardCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		deletePortInfoByBoard(boardCode);
		return count;
	}
	
	private void deletePortInfoByBoard(String boardCode) {
		String status = "5";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Port set serviceStatue='" + status + "' where parentCode='" + boardCode + "'");
		int count = query.executeUpdate();
		tx.commit();
	}

	@Override
	public DeviceCommonInfo getDeviceCommonInfoByDeviceName(String deviceName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		criteria.add(Restrictions.eq("deviceName", deviceName));
		DeviceCommonInfo deviceCommonInfo = (DeviceCommonInfo)criteria.list().get(0);
		tx.commit();
		return deviceCommonInfo;
	}

	@Override
	public boolean updateDeviceFrameStatus(String deviceCode, String frameNum,String status) {
		
		String frameCode = deviceCode + "/" + frameNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Frame set status='" + status + "' where code='" + frameCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateDeviceBoardStatus(String deviceCode, String frameNum,
			String boardNum,String status) {
		
		String boardCode = deviceCode + "/" + boardNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update TerminalUnit set status='" + status + "' where terminalUnitCode='" + boardCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean deleteFrame(String deviceCode, String frameNum) {
		
		String frameCode = deviceCode + "/" + frameNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from Frame where code='" + frameCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean deleteBoard(String deviceCode, String frameNum,
			String boardNum) {
		String boardCode = deviceCode + "/" + boardNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from TerminalUnit where terminalUnitCode='" + boardCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Test
	public void testFrame(){
		String status = "";
		String deviceCode = "";
		String frameNum = "";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Frame set status='" + status + "' where hostCabinet='" + deviceCode + "' and frameNum ='" + frameNum + "'");
		int count = query.executeUpdate();
		tx.commit();
		System.out.println(count);
	}
	
	@Test
	public void testBoard(){
		String status = "2";
		String deviceCode = "ifat_01";
		String frameNum = "2";
		String boardNum = "ifat_board_02";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from TerminalUnit where hostDeviceId='" + deviceCode + "' and frameNum='" + Integer.parseInt(frameNum) + "' and terminalUnitCode='" + boardNum + "'");
		int count = query.executeUpdate();
		tx.commit();
		System.out.println(count);
	}
	
	/**
	 *根据前设备编码更改成为正确的设备编码 
	 */
	@Override
	public boolean updateDeviceCode(String preCode, String newCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Ifdt set code='" + newCode + "' where code='" + preCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	/**
	 * 
	 */
	@Override
	public boolean updateDeviceInfo(String preCode,String newCode,String infos){
		
		//如果设备编码不一致，更新编码
		if(!preCode.equals(newCode)){
			updateDeviceCode(preCode, newCode);
		}
		
		int[] status = new int[infos.length()];
		for(int i = 0;i<infos.length();i++){
			status[i] = Integer.parseInt(infos.substring(i, i+1));
		}
		
		//更新该设备机框信息是
		updateFrameInfos(newCode,status);
		//更新该设备板卡信息
		updateBoardInfos(newCode,status);
		
		return true;
	}

	private void updateFrameInfos(String newCode, int[] status) {
		for(int i = 0;i<4;i++){
			//机框序号 1 2 3 4
			int frameNum = i+1;
			
			//判断是否存在该机框
			boolean exist = judgeFrameExist(newCode,frameNum);
			if(status[i] == 0 && exist){
				//如果与命令中信息一致：status[i] == 0表明设备上无此机框
				deleteFrame(newCode, frameNum+"");
			}
			if(status[i] == 1 && !exist){
				Frame frame = new Frame();
				
				frame.setCode(newCode + "/" + frameNum);
				frame.setHostCabinet(newCode);
				frame.setBoardsCapacity(Constants.boardNumPerFrame);
				frame.setNumberInCabinet(frameNum);
				frame.setPortsCapacity(Constants.boardNumPerFrame * Constants.portNumPerBoard);
				frame.setFrameName(newCode + "/" + frameNum);
				/*
				 机框的一些其他信息
				 */
				addFrame(frame);
			}
		}
	}

	private void updateBoardInfos(String newCode, int[] status) {
		for(int i = 4;i<54;i++){
			//板卡序号：1 2 3 4 5 6 ... 50
			int boardNum = i - 4 + 1;
			
			int temp = boardNum / Constants.boardNumPerFrame;
			int temp0 = boardNum % Constants.boardNumPerFrame;
			int frameNum = temp0 == 0 ? temp : temp+1;
			
			//判断
			boolean exist = judgeBoardExist(newCode,boardNum);
			
			if(status[i] == 0 && exist){
				deleteBoardInfo(newCode, boardNum+"");
			}
			
			if(status[i] != 0 && !exist){
				
				TerminalUnit board = new TerminalUnit();
				
				board.setTerminalUnitCode(newCode + "/" + boardNum);
				board.setTerminalUnitName(newCode + "/" + boardNum);
				board.setHostDeviceId(newCode);
				board.setType(status[i]+"");
				board.setStatus("2");//并且已管理
				board.setColumnNumber(Constants.portNumPerBoard);//
				board.setRowNumber(Constants.rowNumPerBoard);
				board.setOccupiedNum(0);
				board.setDamagedNum(0);
				board.setTerminalNum(Constants.portNumPerBoard * Constants.rowNumPerBoard);
				board.setFrameNum(frameNum);
				board.setTerminalUnitSeq(boardNum+"");
				
				addBoard(board);

			}
			
			if(status[i] != 0 && exist){
				updateBoardStatus(newCode, boardNum+"","2");//如果板卡命令显示已存在，数据库显示也已存在，更改为已管理状态
			}
		}
	}

	private boolean updateBoardStatus(String newCode, String boardNum,String boardStatus) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String code = newCode + "/" + boardNum;
		String sql = "update TerminalUnit set status='" + boardStatus + "' where terminalUnitCode='" + code + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	private boolean deleteBoardInfo(String newCode, String boardNum) {
		
		String boardCode = newCode + "/" + boardNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update TerminalUnit set status='" + 3 + "'where terminalUnitCode='" + boardCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		
		int num = Integer.parseInt(boardNum);
		
		int temp = num / Constants.boardNumPerFrame;
		int temp0 = num % Constants.boardNumPerFrame;
		
		int frameNo = temp0 == 0 ? temp : temp + 1;
		
		for(int i = 1;i<=Constants.portNumPerBoard;i++){
			deletePortInfo(newCode,frameNo,boardNum,i);
		}
		
		return count > 0;
	}

	private void deletePortInfo(String newCode, int frameNo, String boardNum,int portSeq) {
		String portCode = newCode + "/" + frameNo + "/" + boardNum + "/" + portSeq;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Port set importTag='" + 0 + "'where portCode='" + portCode + "'");
		int count = query.executeUpdate();
		tx.commit();
	}

	@Override
	public boolean judgeFrameExist(String deviceCode, int frameNum) {
		
		String frameCode = deviceCode + "/" + frameNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Frame.class);
		criteria.add(Restrictions.eq("code", frameCode));
		int count = criteria.list().size();
		tx.commit();
		
		return  count > 0;
	}

	@Override
	public boolean judgeBoardExist(String deviceCode, int boardNum) {
		
		String boardCode = deviceCode + "/" + boardNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(TerminalUnit.class);
		criteria.add(Restrictions.eq("terminalUnitCode", boardCode));
		int count = criteria.list().size();
		tx.commit();
		return count > 0;
	}

	@Override
	public int addFrameInfo(String deviceCode, int frameNum, int comment) {
		Frame frame = new Frame();
		frame.setHostCabinet(deviceCode);
		frame.setCode(deviceCode + "/" + frameNum);
		return addFrame(frame);
	}

	@Override
	public int addBoardInfo(String deviceCode, int frameNum, int boardNum,
			int comment) {
		TerminalUnit board = new TerminalUnit();
		board.setHostDeviceId(deviceCode);
		board.setTerminalUnitCode(deviceCode + "/" + boardNum);
		board.setTerminalUnitName(deviceCode + "/" + boardNum);
		board.setFrameNum(frameNum);
		
		for(int i = 1;i<=12;i++){
			addPortInfo(deviceCode,frameNum,boardNum,i);
		}
		
		return addBoard(board);
	}

	private Integer addPortInfo(String deviceCode, int frameNum, int boardNum,int portSeq) {
		
		DeviceCommonInfo device = this.getDeviceCommonInfoByDeviceCode(deviceCode);
		
		Port port = new Port();
		port.setCode(deviceCode + "/" + frameNum + "/" + boardNum + "/" + portSeq);
		port.setName(deviceCode + "/" + boardNum + "/" + portSeq);
		port.setServiceStatue("1");
		port.setSlotNo(boardNum);
		port.setMeCode(deviceCode);
		port.setMeType(device.getDeviceType());
		port.setParentCode(deviceCode + "/" + boardNum);
		port.setPhysicalStatue("1");
		port.setServiceStatue("1");
		port.setPortNo(portSeq);
		port.setRowIndex(boardNum);
		port.setColIndex(portSeq);
		port.setCellNo(frameNum);
		
		//port.setPortSequence();
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(port);
		tx.commit();
		return temp;
	}

	/**
	 * 
	 */
	@Override
	public boolean jumpLine(String deviceCode, String aBoard, String aPort,
			String zBoard, String zPort, String comment) {
		int aBoardSeq = Integer.parseInt(aBoard);
		int aPortSeq = Integer.parseInt(aPort);
		int zBoardSeq = Integer.parseInt(zBoard);
		int zPortSeq = Integer.parseInt(zPort);
		
		boolean f1 = updatePortInfo(deviceCode,aBoardSeq,aPortSeq,"2");
		boolean f2 = updatePortInfo(deviceCode,zBoardSeq,zPortSeq,"2");
		
		//添加光纤
		int jumpcode = addJumpConn(deviceCode,aBoardSeq,aPortSeq,zBoardSeq,zPortSeq);
		
		return f1 && f2 && jumpcode != -1;
	}

	private boolean updatePortInfo(String deviceCode, int board, int port,String status) {
		
		int temp = board / Constants.boardNumPerFrame;
		int temp0 = board % Constants.boardNumPerFrame;
		
		int frameNo = temp0 == 0 ? temp : temp + 1;
		
		updateBoardPortsInfo(deviceCode,board,status);
		
		String portCode = deviceCode + "/" + frameNo + "/" + board + "/" + port;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Port set physicalStatue='" + status + "',serviceStatue='" + status + "' where code='" + portCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean removeLine(String deviceCode, String aBoard, String aPort,
			String zBoard, String zPort, String comment) {
		int aBoardSeq = Integer.parseInt(aBoard);
		int aPortSeq = Integer.parseInt(aPort);
		int zBoardSeq = Integer.parseInt(zBoard);
		int zPortSeq = Integer.parseInt(zPort);
		
		boolean f1 = updatePortInfo(deviceCode, aBoardSeq, aPortSeq, "1");
		boolean f2 = updatePortInfo(deviceCode, zBoardSeq, zPortSeq, "1");
		
		//去除光纤
		boolean f3 = removeJumpConn(deviceCode,aBoardSeq,aPortSeq,zBoardSeq,zPortSeq);
		
		return f1 && f2 && f3;
	}

	private boolean removeJumpConn(String deviceCode, int aBoard, int aPort,
			int zBoard, int zPort) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String code = deviceCode + "_" + aBoard + "/" + aPort + "_" + zBoard + "/" + zPort;
		String sql = "delete JumperConnector where code='" + code + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	private Integer addJumpConn(String deviceCode, int aBoard, int aPort, int zBoard,
			int zPort) {
		JumperConnector jumpConn = new JumperConnector();
		
		DeviceCommonInfo device = this.getDeviceCommonInfoByDeviceCode(deviceCode);
		
		//跳接应该是两个设备之间的连接
		String code = deviceCode + "_" + aBoard + "/" + aPort + "_" + zBoard + "/" + zPort;
		
		jumpConn.setCode(code);//
		
		jumpConn.setAmeCode(deviceCode);
		jumpConn.setAcard(aBoard+"");
		jumpConn.setAmeType(device.getDeviceType());
		jumpConn.setAportCode(aPort+"");
		
		jumpConn.setZmeCode(deviceCode);
		jumpConn.setZmeType(device.getDeviceType());
		jumpConn.setZcard(zBoard+"");
		jumpConn.setZportCode(zPort+"");
		
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(jumpConn);
		tx.commit();
		return temp;
	}
	
	private boolean updateBoardPortsInfo(String deviceCode,int boardNum,String type){
		
		String boardCode = deviceCode + "/" + boardNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "";
		if("2".equals(type)){
			sql = "update TerminalUnit set occupiedNum=occupiedNum+1 where terminalUnitCode='" + boardCode + "'";
		}else if("1".equals(type)){
			sql = "update TerminalUnit set occupiedNum=occupiedNum-1 where terminalUnitCode='" + boardCode + "'";
		}
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Test
	public void test(){
		//02004A40 0001 2D7425C3030000EC 01 01 00 00 
		//01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 0000000000000000000000000000000000000000000000000000DC6003
		String deviceCode = "ifdt02";
		String boardNum = "2";
		String type = "1";
		String boardCode = deviceCode + "/" + boardNum;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "";
		if("2".equals(type)){
			sql = "update TerminalUnit set occupiedNum=occupiedNum+1 where terminalUnitCode='" + boardCode + "'";
		}else if("1".equals(type)){
			sql = "update TerminalUnit set occupiedNum=occupiedNum-1 where terminalUnitCode='" + boardCode + "'";
		}
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		System.out.println(count);
	}

	@Override
	public Integer upDatePosition(String deviceCode, int x, int y) {
		DeviceCommonInfo device = this.getDeviceCommonInfoByDeviceCode(deviceCode);
		device.setXposition(x);
		device.setYposition(y);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(device);
		tx.commit();
		return device.getId();
	}
	
	@Test
	public void testGet(){
		List<DeviceCommonInfo> temp = new ArrayList<DeviceCommonInfo>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		temp = criteria.list();
		tx.commit();
		System.out.println(temp.size());
	}
	
	@Test
	public void testPort(){
		new OpticalDeviceDaoImpl().addPortInfo("2D7425C3030000EC", 3, 25, 3);
	}
	
	@Test
	public void testRemoveLine(){
		String deviceCode = "2D7425C3030000EC";
		String aBoard = "2";
		String aPort = "1";
		String zBoard = "13";
		String zPort = "1";
		String comment = "";
		new OpticalDeviceDaoImpl().removeLine(deviceCode, aBoard, aPort, zBoard, zPort, comment);
	}
}
