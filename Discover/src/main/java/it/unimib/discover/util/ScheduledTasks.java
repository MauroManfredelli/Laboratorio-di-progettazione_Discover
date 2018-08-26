package it.unimib.discover.util;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	private static final Logger logger = Logger.getLogger(ScheduledTasks.class);

	@Scheduled(cron = "0 0 0 * * ?")
	public void cronLoyaltyCardCancellata() {
		logger.info("Call cronLoyaltyCardCancellata method");
		try{
			// loyaltyCardService.cronLoyaltyCardCancellata();
		} catch(Exception e){
			logger.error("Error while cronLoyaltyCardCancellata >> ", e);
		}
	}
}