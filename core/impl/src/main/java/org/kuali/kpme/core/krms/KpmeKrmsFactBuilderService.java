package org.kuali.kpme.core.krms;


import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.rice.krms.api.engine.Facts;

public interface KpmeKrmsFactBuilderService {
    public void addFacts(Facts.Builder factsBuilder, String docContent);

    public void addFacts(Facts.Builder factsBuilder, Object factObject);

    public void addFacts(Facts.Builder factsBuilder, Object factObject, String contextId, String namespace);
}
