package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CommonLogger {
	Logger logger;
	
	public <T> CommonLogger(Class<T> cls) {
		logger = Logger.getLogger(cls);
		PropertyConfigurator.configure("/home/santhi-zstk347/ZSmart.properties");
	}
	
	public Logger getLogger() {
		return logger;
	}

	public static void main(String[] args) {
		new CommonLogger(CommonLogger.class).getLogger().info("Testing");
	}

}
