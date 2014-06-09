/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
