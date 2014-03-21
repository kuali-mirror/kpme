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
package org.kuali.kpme.pm.position.web;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrDataObjectMaintainableImpl;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PositionDuty;
import org.kuali.kpme.pm.position.PositionQualification;
import org.kuali.kpme.pm.position.PstnFlag;
import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibility;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PositionMaintainableServiceImpl extends HrDataObjectMaintainableImpl {

	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return (HrBusinessObject) PmServiceLocator.getPositionService().getPosition(id);
	}
	
	@Override
	public void customSaveLogic(HrBusinessObject hrObj){
		PositionBo aPosition = (PositionBo) hrObj;
		for(PositionQualification aQual : aPosition.getQualificationList()) {
			aQual.setHrPositionId(aPosition.getHrPositionId());
			aQual.setPmQualificationId(null);
		}
		for(PositionDuty aDuty : aPosition.getDutyList()) {
			aDuty.setHrPositionId(aPosition.getHrPositionId());
			aDuty.setPmDutyId(null);
		}
		for(PstnFlag aFlag : aPosition.getFlagList()) {
			aFlag.setHrPositionId(aPosition.getHrPositionId());
			aFlag.setPmFlagId(null);
		}
		for(PositionFunding aFunding : aPosition.getFundingList()) {
			aFunding.setHrPositionId(aPosition.getHrPositionId());
			aFunding.setPmPositionFunctionId(null);
		}
        for(PositionDepartment aDepartment : aPosition.getDepartmentList()) {
            aDepartment.setHrPositionId(aPosition.getHrPositionId());
            aDepartment.setPmPositionDeptId(null);
        }
        for(PositionResponsibility aResponsibility : aPosition.getPositionResponsibilityList()) {
        	aResponsibility.setHrPositionId(aPosition.getHrPositionId());
        	aResponsibility.setPositionResponsibilityId(null);
        }
        
        // KPME-3016 populate institution and location here
        // We should be able to do this in addNewLineToCollection, but all the components are in "pages" now with the layout change, 
        // not on the form, and addNewLineToCollection doesn't get called. 
        if (aPosition.getDepartmentList() != null) {
        	for(PositionDepartment aPositionDepartment : aPosition.getDepartmentList()) {
        		if(aPositionDepartment != null && aPositionDepartment.getDeptAffl() != null) {
        			DepartmentAffiliation pda = (DepartmentAffiliation)aPositionDepartment.getDeptAfflObj();
        			if (pda.isPrimaryIndicator()) {
        				aPosition.setLocation(aPositionDepartment.getLocation());
        				aPosition.setInstitution(aPositionDepartment.getInstitution());
        				break;
        			}
        		}
        	}
        }

	}
	
	@Override
    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
            Object addLine) {
        boolean isValid = super.performAddLineValidation(view, collectionGroup, model, addLine);
        if (model instanceof MaintenanceDocumentForm) {
	        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
	        MaintenanceDocument document = maintenanceForm.getDocument();
	        if (document.getNewMaintainableObject().getDataObject() instanceof PositionBo) {
	        	PositionBo aPosition = (PositionBo) document.getNewMaintainableObject().getDataObject();
	        	// Duty line validation
		        if (addLine instanceof PositionDuty) {
		        	PositionDuty pd = (PositionDuty) addLine;
		        	boolean results = this.validateDutyListPercentage(pd, aPosition);
		        	if(!results) {
		        		return false;
		        	}
		        }
	        	// Funding line validation
		        if (addLine instanceof PositionFunding) {
		        	PositionFunding pf = (PositionFunding) addLine;
		        	boolean results = this.validateAddFundingLine(pf, aPosition);
		        	if(!results) {
		        		return false;
		        	}
		        }
	        }
        }

        return isValid;
    }
	
	private boolean validateDutyListPercentage(PositionDuty pd, PositionBo aPosition) {
		if(CollectionUtils.isNotEmpty(aPosition.getDutyList()) && pd.getPercentage() != null) {
			BigDecimal sum = pd.getPercentage();
			for(PositionDuty aDuty : aPosition.getDutyList()) {
				if(aDuty != null && aDuty.getPercentage() != null) {
					sum = sum.add(aDuty.getPercentage());
				}
			}
			if(sum.compareTo(new BigDecimal(100)) > 0) {
				GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.dutyList'].percentage", "duty.percentage.exceedsMaximum", sum.toString());
				return false;
			}
		}		
		return true;
	}
	
	protected boolean validateAddFundingLine(PositionFunding pf, PositionBo aPosition) {
    	if(StringUtils.isNotEmpty(pf.getAccount())) {
    		boolean results = ValidationUtils.validateAccount(pf.getChart(), pf.getAccount());
    		if(!results) {
    			GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.fundingList'].account","error.existence", "Account '" + pf.getAccount() + "'");
    			return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubAccount())) {
    		boolean results = ValidationUtils.validateSubAccount(pf.getSubAccount(), pf.getAccount(), pf.getChart());
    		if(!results) {
	   			 GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.fundingList'].subAccount","error.existence", "Sub Account '" + pf.getSubAccount() + "'");
	   			 return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getObjectCode()) && aPosition.getEffectiveDate() != null) {
    		boolean results = ValidationUtils.validateObjectCode(pf.getObjectCode(), pf.getChart(), Integer.valueOf(aPosition.getEffectiveLocalDate().getYear()));
    		if(!results) {
    			 GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.fundingList'].objectCode","error.existence", "Object Code '" + pf.getObjectCode() + "'");
      			 return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubObjectCode())) {
    		boolean results = ValidationUtils.validateSubObjectCode(String.valueOf(aPosition.getEffectiveLocalDate().getYear()),
    				pf.getChart(),
    				pf.getAccount(),
    				pf.getObjectCode(),
    				pf.getSubObjectCode());
    		if(!results) {
    			 GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.fundingList'].subObjectCode","error.existence", "Sub Object Code '" + pf.getSubObjectCode() + "'");
      			 return results;
    		}
    	}
    	return true;
    
	}

	
	// KPME-3016
	//set document description here so it passes validation.  It will get overriden in doRouteStatusChange method
	@Override
	public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        document.getDocumentHeader().setDocumentDescription("Edit Position");
        super.processAfterEdit(document, requestParameters);
    }
	@Override 
	public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        PositionBo aPosition = (PositionBo) document.getNewMaintainableObject().getDataObject();
        aPosition.setProcess("New");

        document.getDocumentHeader().setDocumentDescription("New Position");
		super.processAfterNew(document, requestParameters);
	}
	
	@Override
	public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> parameters) {
        PositionBo aPosition = (PositionBo) document.getNewMaintainableObject().getDataObject();
        aPosition.setProcess("New");
        aPosition.setPositionNumber(null);

        document.getDocumentHeader().setDocumentDescription("New Position");
		super.processAfterCopy(document, parameters);
	}

    @Override
    public String getDocumentTitle(MaintenanceDocument document) {
        String docTitle = document.getDocumentHeader().getDocumentDescription();
        return docTitle;
    }

	@Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {

		String docDescription = null;
		PositionBo position = (PositionBo)this.getDataObject();
		DocumentStatus documentStatus = documentHeader.getWorkflowDocument().getStatus();
	
		//Set document description for real here
		if (StringUtils.isEmpty(position.getPositionNumber())) {
			docDescription = "Process: " + position.getProcess() + " Position Status: " + position.getPositionStatus();
		} else {
			docDescription = "Process: " + position.getProcess() + " Position Number: " + position.getPositionNumber() + " Position Status: " + position.getPositionStatus();
		}

		if (DocumentStatus.ENROUTE.equals(documentStatus)) {
			try {
				MaintenanceDocument md = (MaintenanceDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentHeader.getDocumentNumber());
		        md.getDocumentHeader().setDocumentDescription(docDescription);
		        md.getNewMaintainableObject().setDataObject(position);
		        KRADServiceLocatorWeb.getDocumentService().saveDocument(md);
			} catch (WorkflowException e) {
	            LOG.error("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
	            throw new RuntimeException("caught exception while handling doRouteStatusChange -> documentService.getByDocumentHeaderId(" + documentHeader.getDocumentNumber() + "). ", e);
	        }
		}
    }

    //KPME-2624 added logic to save current logged in user to UserPrincipal id for collections
    @Override
    public void prepareForSave() {
    	PositionBo position = (PositionBo)this.getDataObject();
        boolean hasPrimaryDepartment = false;
        for (PositionDepartment positionDepartment : position.getDepartmentList()) {
            if (positionDepartment.getDeptAfflObj().isPrimaryIndicator()) {
                hasPrimaryDepartment=true;
                positionDepartment.setDepartment(position.getPrimaryDepartment());
                positionDepartment.setLocation(position.getLocation());
                positionDepartment.setInstitution(position.getInstitution());
                positionDepartment.setDeptAffl(HrServiceLocator.getDepartmentAffiliationService().getPrimaryAffiliation().getDeptAfflType());
            }
        }

        //create primary department
        if (!hasPrimaryDepartment && StringUtils.isNotEmpty(position.getPrimaryDepartment())) {
            PositionDepartment primaryDepartment = new PositionDepartment();
            primaryDepartment.setDepartment(position.getPrimaryDepartment());
            primaryDepartment.setLocation(position.getLocation());
            primaryDepartment.setInstitution(position.getInstitution());
            primaryDepartment.setDeptAffl(HrServiceLocator.getDepartmentAffiliationService().getPrimaryAffiliation().getDeptAfflType());
            position.getDepartmentList().add(primaryDepartment);
        }

        //add note if enroute change occurs
            try {
                MaintenanceDocument maintenanceDocument = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(this.getDocumentNumber());
                if (maintenanceDocument != null && maintenanceDocument.getNewMaintainableObject().getDataObject() instanceof PositionBo) {
                    PositionBo previousPosition = (PositionBo) maintenanceDocument.getNewMaintainableObject().getDataObject();
                    recordEnrouteChanges(previousPosition,maintenanceDocument.getNoteTarget().getObjectId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        super.prepareForSave();


    }

    private void recordEnrouteChanges(PositionBo previousPosition, String noteTarget) {
        //List of fields on the position class not to compare
        List<String> noCompareFields = new ArrayList<String>();
        noCompareFields.add("process");
        noCompareFields.add("requiredQualList");

        List<Note> noteList = new ArrayList<Note>();
        PositionBo currentPosition = (PositionBo) this.getDataObject();

        EntityNamePrincipalName approver = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(currentPosition.getUserPrincipalId());

        //compare all fields on position
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(PositionBo.class).getPropertyDescriptors()) {

                if (pd.getReadMethod() != null && !noCompareFields.contains(pd.getName())) {
                    try {
                        Object currentObject = pd.getReadMethod().invoke(currentPosition);
                        Object previousObject = pd.getReadMethod().invoke(previousPosition);


                            if (!currentObject.equals(previousObject)){
                                    String noteText = approver.getPrincipalName() + " changed " + pd.getDisplayName() + " from '" + previousObject.toString() + "' to '" + currentObject.toString() + "'";

                                    Note note = new Note();
                                    note.setRemoteObjectIdentifier(noteTarget);
                                    note.setNoteText(StringUtils.abbreviate(noteText,800));
                                    note.setAuthorUniversalIdentifier(currentPosition.getUserPrincipalId());
                                    note.setNotePostedTimestampToCurrent();
                                    noteList.add(note);
                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        KRADServiceLocator.getNoteService().saveNoteList(noteList);
    }
}
