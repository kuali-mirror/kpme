package org.kuali.kpme.core.api.groupkey;

import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.rice.location.api.campus.CampusContract;

public interface HrGroupKeyContract extends KpmeEffectiveDataTransferObject {
    String getGroupKeyCode();
    String getLocationId();
    String getCampusCode();
    String getInstitutionCode();

    String getDescription();

    LocationContract getLocation();
    CampusContract getCampus();
    InstitutionContract getInstitution();

}
