package org.kuali.kpme.core.api.mo;

import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Identifiable;
import org.kuali.rice.core.api.mo.common.Versioned;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;

/**
 * Created by jjhanso on 3/10/14.
 */
public interface KpmeEffectiveDataTransferObject extends Versioned, GloballyUnique, Inactivatable, Identifiable, Effective, UserModified {
}
