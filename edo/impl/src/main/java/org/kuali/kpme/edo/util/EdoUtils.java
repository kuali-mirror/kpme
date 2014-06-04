package org.kuali.kpme.edo.util;

import org.kuali.rice.core.api.config.property.ConfigContext;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EdoUtils {
	public static Date getPlaceholderDate(){
		Calendar cal = GregorianCalendar.getInstance();
		
		return new Date(cal.getTimeInMillis());
	}
	public static Date getNow() {
		return new Date(System.currentTimeMillis());
	}

    public static Timestamp getNowTS() {
        return new Timestamp(System.currentTimeMillis());
    }
	public static Date convertDateIfNecessary(Date date){
		if(getPlaceholderDate().equals(date)){
			return null;
		}
		return date;
	}

    public static String BuildUploadPath(String userName, String itemID) {

        String UPLOAD_DIRECTORY = "upload";
        // constructs the directory path to store upload file
        String uploadPath = ConfigContext.getCurrentContextConfig().getProperty("edo.upload.path");

        // creates the directory if it does not exist
        uploadPath += (File.separator + UPLOAD_DIRECTORY);
        File oneUploadDir = new File(uploadPath);
        if (!oneUploadDir.exists()) {
            boolean mkdirResult = oneUploadDir.mkdir();
            if (!mkdirResult) {
                return null;
            }
        }
        uploadPath += (File.separator + userName);
        File twoUploadDir = new File(uploadPath);
        if (!twoUploadDir.exists()) {
            boolean mkdirResult = twoUploadDir.mkdir();
            if (!mkdirResult) {
                return null;
            }
        }
        uploadPath += (File.separator + itemID);
        File threeUploadDir = new File(uploadPath);
        if (!threeUploadDir.exists()) {
            boolean mkdirResult = threeUploadDir.mkdir();
            if (!mkdirResult) {
                return null;
            }
        }

        return uploadPath;
    }

}
