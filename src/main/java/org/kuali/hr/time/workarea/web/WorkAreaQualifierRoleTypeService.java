package org.kuali.hr.time.workarea.web;


/**
 * This class is required to enable role-qualifiers on the roles we add to the system.  
 * For it to work, you need to have inserted a row into the table:
 * 
 * 'KRIM_TYP_T'
 * 
 * Additionally, in SpringBeans.xml you will need to have service definition that defines this
 * class.  Make sure that your entry in KRIM_TYP_T references this class by bean name.
 * 
 * To add attributes to the role, you need to have the KRIM_ATTRIBUTE set up:
 * 
 * KimAttributeImpl documentTypeAttribute = new KimAttributeImpl();
 * documentTypeAttribute.setKimAttributeId("30");
 * documentTypeAttribute.setAttributeName("workAreaId");
 * documentTypeAttribute.setNamespaceCode("KUALI");
 * documentTypeAttribute.setAttributeLabel("");
 * documentTypeAttribute.setComponentName(KimAttributes.class.getName());
 * documentTypeAttribute.setActive(true);
 * KNSServiceLocator.getBusinessObjectService().save(documentTypeAttribute);
 * 
 * Set the variables appropriately.  This only needs to be done once ever.
 * 
 * @author djunk
 *
 */
public class WorkAreaQualifierRoleTypeService{

//	@Override
//	public List<RoleMembershipInfo> doRoleQualifiersMatchQualification(AttributeSet qualification, List<RoleMembershipInfo> roleMemberList) {
//		List<RoleMembershipInfo> matchingMemberships = new ArrayList<RoleMembershipInfo>();
//		for (RoleMembershipInfo rmi : roleMemberList) {			
//			if (performMatch(qualification, rmi.getQualifier())) {
//				matchingMemberships.add(rmi);
//			}
//		}
//		return matchingMemberships;
//	}
	
	/**
	 * The default performMatch is implemented in a way that makes it un-usable, even though they are
	 * doing esentially the same thing.
	 * 
	 * This method will fail fast on its conditionals.  It is intentionally verbose.
	 */
//	@Override
//	public boolean performMatch(AttributeSet qualifier, AttributeSet stored) {
//		boolean matches = true;
//		
//		if (qualifier == null) {
//			return true;
//		}
//		
//		if (stored == null) {
//			return false;
//		}
//		
//		for (String key : qualifier.keySet()) {
//			if (!stored.containsKey(key)) {
//				return false;
//			} else {
//				String storedValue = stored.get(key);
//				String qualifierValue = qualifier.get(key);
//				
//				matches = StringUtils.equals(qualifierValue, storedValue);
//				if (!matches) {
//					return false;
//				}
//			}
//		}
//		
//		
//		return matches;
//	}

}
