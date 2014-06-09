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


