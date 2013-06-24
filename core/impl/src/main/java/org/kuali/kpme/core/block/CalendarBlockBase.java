package org.kuali.kpme.core.block;

import java.sql.Timestamp;
import java.util.Date;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class CalendarBlockBase extends PersistableBusinessObjectBase implements CalendarBlockContract {

	protected String principalId;
	protected String documentId;
	protected String principalIdModified;
	protected Date beginTimestamp;
	protected Date endTimestamp;
	protected Timestamp timestamp;
	
	public CalendarBlockBase() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getDocumentId()
	 */
	@Override
	public String getDocumentId() {
		return documentId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#setDocumentId(java.lang.String)
	 */
	@Override
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getPrincipalIdModified()
	 */
	@Override
	public abstract String getPrincipalIdModified();

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#setPrincipalIdModified(java.lang.String)
	 */
	@Override
	public abstract void setPrincipalIdModified(String principalIdModified);

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getPrincipalId()
	 */
	@Override
	public String getPrincipalId() {
		return principalId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#setPrincipalId(java.lang.String)
	 */
	@Override
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getTimestamp()
	 */
	@Override
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#setTimestamp(java.sql.Timestamp)
	 */
	@Override
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getBeginTimestamp()
	 */
	@Override
	public Date getBeginTimestamp() {
		return beginTimestamp;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#setBeginTimestamp(java.util.Date)
	 */
	@Override
	public void setBeginTimestamp(Date beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getEndTimestamp()
	 */
	@Override
	public Date getEndTimestamp() {
		return endTimestamp;
	}

	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#setEndTimestamp(java.util.Date)
	 */
	@Override
	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
	


}