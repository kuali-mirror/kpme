package org.kuali.hr.core.role.workarea;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.kuali.hr.core.role.PositionRoleMemberBo;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectComplete;
import org.kuali.rice.core.api.mo.common.active.InactivatableFromToUtils;
import org.kuali.rice.kim.api.role.RoleMemberContract;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

@SuppressWarnings("unchecked")
public class WorkAreaPositionRoleMemberBo extends PositionRoleMemberBo {

	private static final long serialVersionUID = -321921468708618676L;

	public static WorkAreaPositionRoleMemberBo from(RoleMemberBo roleMemberBo, Map<String, String> attributes) {
        if (roleMemberBo == null) { 
        	return null;
        }

        WorkAreaPositionRoleMemberBo.Builder b = new WorkAreaPositionRoleMemberBo.Builder(roleMemberBo.getRoleId(), roleMemberBo.getMemberId(), roleMemberBo.getType());
        b.setId(roleMemberBo.getId());
        b.setActiveFromDate(roleMemberBo.getActiveFromDate());
        b.setActiveToDate(roleMemberBo.getActiveToDate());
        b.setObjectId(roleMemberBo.getObjectId());
        b.setVersionNumber(roleMemberBo.getVersionNumber());
        
        b.setAttributes(attributes);
        
        return b.build();
    }
	
	public WorkAreaPositionRoleMemberBo() {
		super();
	}
	
    private WorkAreaPositionRoleMemberBo(Builder b) {
        setId(b.getId());
        setRoleId(b.getRoleId());
        setAttributes(b.getAttributes());
        setMemberId(b.getMemberId());
        setTypeCode(b.getType().getCode());
        setActiveFromDateValue(b.getActiveFromDate() == null ? null : new Timestamp(b.getActiveFromDate().getMillis()));
        setActiveToDateValue(b.getActiveToDate() == null ? null : new Timestamp(b.getActiveToDate().getMillis()));
        setVersionNumber(b.getVersionNumber());
        setObjectId(b.getObjectId());
    }
	
	public static final class Builder implements ModelBuilder, ModelObjectComplete {

		private static final long serialVersionUID = -8388185252029805836L;

		private String id;
        private String roleId;
        private Map<String, String> attributes;
        private String memberId;
        private MemberType type;
        private DateTime activeFromDate;
        private DateTime activeToDate;
        private Long versionNumber;
        private String objectId;

        private Builder(String roleId, String memberId, MemberType type) {
            setRoleId(roleId);
            setMemberId(memberId);
            setType(type);
        }

        public static Builder create(String roleId, String id, String memberId,
                                     MemberType memberType, DateTime activeFromDate, DateTime activeToDate, Map<String, String> attributes,String memberName,String memberNamespaceCode) {
            Builder b = new Builder(roleId, memberId, memberType);
            b.setId(id);
            b.setActiveFromDate(activeFromDate);
            b.setActiveToDate(activeToDate);
            b.setAttributes(attributes);
            return b;
        }

        public static Builder create(RoleMemberContract contract) {
            Builder b = new Builder(contract.getRoleId(), contract.getMemberId(), contract.getType());
            b.setId(contract.getId());
            b.setAttributes(contract.getAttributes());
            b.setActiveFromDate(contract.getActiveFromDate());
            b.setActiveToDate(contract.getActiveToDate());
            b.setVersionNumber(contract.getVersionNumber());
            b.setObjectId(contract.getObjectId());
            return b;
        }

        @Override
        public WorkAreaPositionRoleMemberBo build() {
            return new WorkAreaPositionRoleMemberBo(this);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            if (StringUtils.isEmpty(roleId)) {
                throw new IllegalArgumentException("roleId is empty");
            }
            this.roleId = roleId;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            if (StringUtils.isBlank(memberId)) {
                throw new IllegalArgumentException("memberId may not be null");
            }
            this.memberId = memberId;
        }

        public MemberType getType() {
            return type;
        }

        public void setType(final MemberType type) {
            if (type == null) {
                throw new IllegalArgumentException("type is null");
            }
            this.type = type;
        }

        public DateTime getActiveFromDate() {
            return activeFromDate;
        }

        public void setActiveFromDate(DateTime activeFromDate) {
            this.activeFromDate = activeFromDate;
        }

        public DateTime getActiveToDate() {
            return activeToDate;
        }

        public void setActiveToDate(DateTime activeToDate) {
            this.activeToDate = activeToDate;
        }

        public boolean isActive(DateTime activeAsOfDate) {
            return InactivatableFromToUtils.isActive(activeFromDate, activeToDate, activeAsOfDate);
        }

        public boolean isActive() {
            return InactivatableFromToUtils.isActive(activeFromDate, activeToDate, null);
        }

        public Long getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(final Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(final String objectId) {
            this.objectId = objectId;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(obj, this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

}