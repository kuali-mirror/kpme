package org.kuali.hr.core.permission;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kew.api.document.DocumentStatus;

public enum KPMEDocumentStatus {
	
	STARTED (DocumentStatus.INITIATED, DocumentStatus.SAVED),
	ENROUTE (DocumentStatus.ENROUTE, DocumentStatus.EXCEPTION),
	FINAL (DocumentStatus.PROCESSED, DocumentStatus.FINAL, DocumentStatus.DISAPPROVED, DocumentStatus.CANCELED, DocumentStatus.RECALLED);
	
	private static final Map<DocumentStatus, KPMEDocumentStatus> DOCUMENT_STATUSES = new EnumMap<DocumentStatus, KPMEDocumentStatus>(DocumentStatus.class);
	
	static {
		for (KPMEDocumentStatus kpmeDocumentStatus : KPMEDocumentStatus.values()) {
			for (DocumentStatus documentStatus : kpmeDocumentStatus.getDocumentStatuses()) {
				DOCUMENT_STATUSES.put(documentStatus, kpmeDocumentStatus);
			}
		}
	}
	
	public static KPMEDocumentStatus getKPMEDocumentStatus(DocumentStatus documentStatus) {
		return DOCUMENT_STATUSES.get(documentStatus);
	}
	
	private List<DocumentStatus> documentStatuses = new ArrayList<DocumentStatus>();
	
	private KPMEDocumentStatus(DocumentStatus... documentStatuses) {
		for (DocumentStatus documentStatus : documentStatuses) {
			this.documentStatuses.add(documentStatus);
		}
	}

	public List<DocumentStatus> getDocumentStatuses() {
		return documentStatuses;
	}

	public void setDocumentStatuses(List<DocumentStatus> documentStatuses) {
		this.documentStatuses = documentStatuses;
	}

}