package org.kuali.khr.hub.util;


public class KhrTestConstants {

	public static String BASE_URL = "http://localhost:8080/kpme-dev";

	public static class Urls {
		public static final String CLOCK_PAGE_URL = BASE_URL + "/Clock.do";
		public static final String TIME_DETAIL_PAGE_URL = BASE_URL + "/TimeDetail.do";
		public static final String LOGOUT_URL = BASE_URL + "/logout.do";
		public static final String POSITION_LOOKUP_URL = BASE_URL
				+ "/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.position.PositionBo";

	}


}