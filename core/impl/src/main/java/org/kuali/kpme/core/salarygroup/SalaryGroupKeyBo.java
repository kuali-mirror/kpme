package org.kuali.kpme.core.salarygroup;

import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class SalaryGroupKeyBo extends HrBusinessObjectKey<SalaryGroupBo, SalaryGroupKeyBo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3035597915412860618L;
	
	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfSalaryGroupKey, SalaryGroupKeyBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<EffectiveKey, SalaryGroupKeyBo> toBo =
		new ModelObjectUtils.Transformer<EffectiveKey, SalaryGroupKeyBo>() {
			public SalaryGroupKeyBo transform(EffectiveKey input) {
				return SalaryGroupKeyBo.from(input);
			};
		};

	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transformSet(setOfSalaryGroupKeyBo, SalaryGroupKeyBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<SalaryGroupKeyBo, EffectiveKey> toImmutable =
		new ModelObjectUtils.Transformer<SalaryGroupKeyBo, EffectiveKey>() {
			public EffectiveKey transform(SalaryGroupKeyBo input) {
				return SalaryGroupKeyBo.to(input);
			};
		};

	@Override
	public SalaryGroupBo getOwner() {
		return super.getOwner();
	}
	
	@Override
	public void setOwner(SalaryGroupBo owner) {
		super.setOwner(owner);
	}
	
	public static SalaryGroupKeyBo from(EffectiveKey im) {
		return commonFromLogic(im, new SalaryGroupKeyBo());
	}

}
