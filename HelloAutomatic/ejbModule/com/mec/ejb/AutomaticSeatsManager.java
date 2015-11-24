package com.mec.ejb;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.mec.ejb.dao.SeatDAO;
import com.mec.ejb.dao.SeatTypeDAO;
import com.mec.ejb.inter.Logger;
import com.mec.pojo.entity.Seat;
import com.mec.pojo.entity.SeatType;
import com.mec.pojo.entity.SeatType.SeatPosition;

//@Singleton
@Startup
public class AutomaticSeatsManager {
	
//	@Resource
	private TimerService timer;
	
	@Inject
	private Logger logger;
	
//	@Inject
//	private EntityManager em;
	
	@Inject
	private SeatDAO seatDao;
	@Inject
	private SeatTypeDAO seatTypeDao;
	@Inject
	private Event<Seat> event;
	
	@Inject
	private Event<SeatType> typeEvent;
	
	@Schedule(hour="*", minute="*", second="*/10", persistent=false)
	public void populatesSeats(){
		SeatType type = new SeatType("Derp Type" , (int)(100 * Math.random()), (int)(10*Math.random()), SeatPosition.BALCONY);
		seatTypeDao.persist(type);
//		typeEvent.fire(type);
		logger.info("new seat Type persisted");
		
		
		Seat s = new Seat("Derp Seat" + randId(), type.getPrice(), false, type);
		seatDao.persist(s);
		event.fire(s);
		logger.info("new seat persisted");
	}
	
	@Schedule(hour="*", minute="*/2", persistent=false)
	public void clearSeats(){
		seatDao.deleteAll();
		seatTypeDao.deleteAll();
		logger.info("All seats and seat types are cleared");
	}
	
	
	private static String randId(){
		String randomStr = Long.toString(System.currentTimeMillis());
		return randomStr.substring(randomStr.length() - 4);
	}

}
