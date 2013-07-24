package org.kuali.kpme.core.krms;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.xml.XmlHelper;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.krms.api.KrmsApiServiceLocator;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.repository.category.CategoryDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public abstract class KpmeKrmsFactBuilderServiceHelper implements KpmeKrmsFactBuilderService {
    protected final Logger LOG = Logger.getLogger(KpmeKrmsFactBuilderServiceHelper.class);

    protected String getElementValue(String docContent, String xpathExpression) {
        try {
            Document document = XmlHelper.trimXml(new ByteArrayInputStream(docContent.getBytes()));

            XPath xpath = XPathHelper.newXPath();
            String value = (String) xpath.evaluate(xpathExpression, document, XPathConstants.STRING);

            return value;

        } catch (Exception e) {
            throw new RiceRuntimeException();
        }
    }

    /**
     *
     * This method gets all terms from the <code>factsObject</code>and add it to the KRMS FactsBuilder
     * @param factsBuilder
     * @param factsObject
     * @param contextId
     * @param factTermNS
     */
    public void addObjectMembersAsFacts(Facts.Builder factsBuilder, Object factsObject,String contextId, String factTermNS) {
        TermRepositoryService termRepositoryService = HrServiceLocator.getService("termRepositoryService");
        //TermSpecificationDefinition termSpec = termRepositoryService.getTermSpecificationByNameAndNamespace("KPME_Term_SPEC", "KPME_NMSPC");
        //List<TermSpecificationDefinition> termSpecs = Collections.singletonList(termSpec);
        List<TermSpecificationDefinition> termSpecs=(List<TermSpecificationDefinition>) termRepositoryService.findAllTermSpecificationsByContextId(contextId);
        for (TermSpecificationDefinition termSpecificationDefinition : termSpecs) {

            if(isPropertyType(termSpecificationDefinition)){
                String termNS = termSpecificationDefinition.getNamespace();
                if(termNS.equals(factTermNS)){
                    String factKey = termSpecificationDefinition.getName();
                    if(factsObject!=null){
                        Class factsClazz = factsObject.getClass();
                        PropertyDescriptor propDescriptor = null;
                        try {
                            propDescriptor = PropertyUtils.getPropertyDescriptor(factsObject, factKey);
                            if(propDescriptor!=null){
                                Object propertyValue = null;
                                Method readMethod = propDescriptor.getReadMethod();
                                if(readMethod!=null){
                                    propertyValue = propDescriptor.getReadMethod().invoke(factsObject);
                                }
                                factsBuilder.addFact(factKey, propertyValue);
                            }
                        }catch (IllegalArgumentException e) {
                            LOG.error("KRMS Fact for " + factKey + " has not been added to fact builder", e);
                        }catch (IllegalAccessException e) {
                            LOG.error("KRMS Fact for " + factKey + " has not been added to fact builder", e);
                        }catch (InvocationTargetException e) {
                            LOG.error("KRMS Fact for " + factKey + " has not been added to fact builder", e);
                        }catch (NoSuchMethodException e) {
                            LOG.error("KRMS Fact for " + factKey + " has not been added to fact builder", e);
                        }
                    }else{
                        factsBuilder.addFact(factKey, null);
                    }
                }
            }
        }
    }

    private boolean isPropertyType(TermSpecificationDefinition termSpecificationDefinition) {
        List<CategoryDefinition> catgories = termSpecificationDefinition.getCategories();
        for (CategoryDefinition categoryDefinition : catgories) {
            if(categoryDefinition.getName().equals("Property"))
                return true;
        }
        return false;
    }
}
