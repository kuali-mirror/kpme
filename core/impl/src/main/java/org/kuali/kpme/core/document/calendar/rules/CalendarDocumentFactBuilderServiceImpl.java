package org.kuali.kpme.core.document.calendar.rules;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderServiceHelper;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.xml.XmlHelper;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.Term;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CalendarDocumentFactBuilderServiceImpl extends KpmeKrmsFactBuilderServiceHelper {

    @Override
    public void addFacts(Facts.Builder factsBuilder, String docContent) {

    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factsObject) {
        CalendarDocument document = (CalendarDocument)factsObject;
        addObjectMembersAsFacts(factsBuilder,document,"KPME-TK-CONTEXT","KPME_TIMESHEET");
        factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.FALSE);
        if (document != null) {

            List<String> workAreas = new ArrayList<String>();
            List<String> depts = new ArrayList<String>();

            for (Assignment a : document.getAssignments()) {
                workAreas.add(String.valueOf(a.getWorkArea()));
                depts.add(a.getDept());
                Department department = HrServiceLocator.getDepartmentService().getDepartment(a.getDept(), a.getEffectiveLocalDate());
                if (department != null
                        && department.isPayrollApproval()) {
                    factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.TRUE);
                }
            }
            if (CollectionUtils.isNotEmpty(depts)) {
                factsBuilder.addFact("department", depts);
            }
            if (CollectionUtils.isNotEmpty(workAreas)) {
                factsBuilder.addFact("workarea", workAreas);
            }
        }
    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factsObject, String contextId, String namespace) {
        CalendarDocument document = (CalendarDocument)factsObject;
        addObjectMembersAsFacts(factsBuilder, document, contextId, namespace);
        factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.FALSE);
        if (document != null) {

            Set<String> workAreas = new HashSet<String>();
            Set<String> depts = new HashSet<String>();

            for (Assignment a : document.getAssignments()) {
                workAreas.add(String.valueOf(a.getWorkArea()));
                depts.add(a.getDept());
                Department department = HrServiceLocator.getDepartmentService().getDepartment(a.getDept(), a.getEffectiveLocalDate());
                if (department != null
                        && department.isPayrollApproval()) {
                    factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.TRUE);
                }
            }
            if (CollectionUtils.isNotEmpty(depts)) {
                factsBuilder.addFact("department", depts);
            }
            if (CollectionUtils.isNotEmpty(workAreas)) {
                factsBuilder.addFact("workarea", workAreas);
            }
        }
    }

    protected String getElementValue(String docContent, String xpathExpression) {
        try {
            Document document = XmlHelper.trimXml(new ByteArrayInputStream(docContent.getBytes()));

            XPath xpath = XPathHelper.newXPath();
            String value = (String) xpath.evaluate(xpathExpression, document, XPathConstants.STRING);

            return value;

        } catch (Exception e) {
            throw new RiceRuntimeException(e);
        }
    }
}
