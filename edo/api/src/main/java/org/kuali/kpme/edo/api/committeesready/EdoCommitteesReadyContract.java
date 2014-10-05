package org.kuali.kpme.edo.api.committeesready;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.mo.CreateTime;
import org.kuali.kpme.core.api.mo.KeyedData;
import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;
import org.kuali.kpme.core.api.mo.UserModified;

/**
 * <p>EdoCommitteesReadyContract interface</p>
 *
 */
public interface EdoCommitteesReadyContract extends KpmeDataTransferObject, UserModified, KeyedData, CreateTime {

	/**
	 * The identifier of the EdoCommitteesReady
	 * 
	 * <p>
	 * edoCommitteesReadyId of the EdoCommitteesReady
	 * <p>
	 * 
	 * @return edoCommitteesReadyId for EdoCommitteesReady
	 */
	public String getEdoCommitteesReadyId();

	/**
	 * The flag to indicate if it's ready to submit dossier
	 * 
	 * <p>
	 * ready of the EdoCommitteesReady
	 * <p>
	 * 
	 * @return ready for EdoCommitteesReady
	 */
	public boolean isReady();
}
