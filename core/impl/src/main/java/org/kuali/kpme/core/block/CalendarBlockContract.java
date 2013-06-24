package org.kuali.kpme.core.block;

import java.sql.Timestamp;
import java.util.Date;

public interface CalendarBlockContract {

	public String getDocumentId();

	public void setDocumentId(String documentId);

	public abstract String getPrincipalIdModified();

	public abstract void setPrincipalIdModified(String principalIdModified);

	public String getPrincipalId();

	public void setPrincipalId(String principalId);

	public Timestamp getTimestamp();

	public void setTimestamp(Timestamp timestamp);

	public Date getBeginTimestamp();

	public void setBeginTimestamp(Date beginTimestamp);

	public Date getEndTimestamp();

	public void setEndTimestamp(Date endTimestamp);

}