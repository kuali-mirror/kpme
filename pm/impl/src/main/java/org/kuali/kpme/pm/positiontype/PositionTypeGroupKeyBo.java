package org.kuali.kpme.pm.positiontype;

import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.pm.positiontype.PositionTypeBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

/**
 * Created by mlemons on 6/3/14.
 */
public class PositionTypeGroupKeyBo extends HrBusinessObjectKey<PositionTypeBo, PositionTypeGroupKeyBo> {

//    private static final long serialVersionUID = 3035597915412860604L;

    public static final ModelObjectUtils.Transformer<EffectiveKey, PositionTypeGroupKeyBo> toBo =
            new ModelObjectUtils.Transformer<EffectiveKey, PositionTypeGroupKeyBo>() {
                public PositionTypeGroupKeyBo transform(EffectiveKey input) {
                    return PositionTypeGroupKeyBo.from(input);
                };
            };

    public static final ModelObjectUtils.Transformer<PositionTypeGroupKeyBo, EffectiveKey> toImmutable =
            new ModelObjectUtils.Transformer<PositionTypeGroupKeyBo, EffectiveKey>() {
                public EffectiveKey transform(PositionTypeGroupKeyBo input) {
                    return PositionTypeGroupKeyBo.to(input);
        };
    };

    @Override
    public PositionTypeBo getOwner() {
        return super.getOwner();
    }

    @Override
    public void setOwner(PositionTypeBo owner) {
        super.setOwner(owner);
    }

    public static PositionTypeGroupKeyBo from(EffectiveKey im) {
        return commonFromLogic(im, new PositionTypeGroupKeyBo());
    }
}


