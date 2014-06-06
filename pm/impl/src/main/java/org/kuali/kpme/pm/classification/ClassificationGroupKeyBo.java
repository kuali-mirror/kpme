package org.kuali.kpme.pm.classification;

import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

/**
 * Created by mlemons on 6/5/14.
 */
public class ClassificationGroupKeyBo extends HrBusinessObjectKey<ClassificationBo, ClassificationGroupKeyBo> {

    public static final ModelObjectUtils.Transformer<EffectiveKey, ClassificationGroupKeyBo> toBo =
            new ModelObjectUtils.Transformer<EffectiveKey, ClassificationGroupKeyBo>() {
                public ClassificationGroupKeyBo transform(EffectiveKey input) {
            return ClassificationGroupKeyBo.from(input);
        };
    };

    public static final ModelObjectUtils.Transformer<ClassificationGroupKeyBo, EffectiveKey> toImmutable =
            new ModelObjectUtils.Transformer<ClassificationGroupKeyBo, EffectiveKey>() {
                public EffectiveKey transform(ClassificationGroupKeyBo input) {
                    return ClassificationGroupKeyBo.to(input);
                };
            };

    @Override
    public ClassificationBo getOwner()
    {
        return super.getOwner();
    }

    @Override public void setOwner(ClassificationBo owner)
    {
        super.setOwner(owner);
    }

    public static ClassificationGroupKeyBo from (EffectiveKey im) {
        return commonFromLogic(im, new ClassificationGroupKeyBo());
    }
}
