package org.kuali.kpme.edo.workflow.node;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.document.PromotionSupplementalProcessDocument;
import org.kuali.kpme.edo.document.TenureSupplementalProcessDocument;
import org.kuali.rice.kew.doctype.bo.DocumentType;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.Serializable;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 8/9/13
 * Time: 11:47 AM
 */
public class EdoSupplementalSplitChairsNode implements SplitNode {

    public SplitResult process(RouteContext context, RouteHelper helper) throws Exception {
        SplitResult result = null;
      String documentType = context.getDocument().getDocumentType().getName(); 
      String documentContent = context.getDocumentContent().getDocContent();
      Document document = loadXMLFrom(new java.io.ByteArrayInputStream(documentContent.getBytes()));
      if(StringUtils.equals(documentType, "TenureSupplementalProcessDocument")){
    	  result = findNotifyBranches(document);
    	}
      
      if(StringUtils.equals(documentType, "PromotionSupplementalProcessDocument")){
    	  result = findNotifyBranches(document);
      }
       	return result;
    }
    
    public SplitResult findNotifyBranches(Document  document) throws XPathExpressionException {
 	   List<String> branchNames = new ArrayList<String>();
 	   	Element root = document.getDocumentElement();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String xpathExpr =  "/documentContent/applicationContent/dossier/authorizedNodes/authorizedNode";
        NodeList nodeList = (NodeList) xpath.compile(xpathExpr).evaluate(root, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
        	branchNames.add(nodeList.item(i).getFirstChild().getNodeValue());
        }
 	 
 	
 	return new SplitResult(branchNames);

 }
    //this is used to convert the xml which is in string format to the DOM format
    public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is) 
    	    throws org.xml.sax.SAXException, java.io.IOException {
    	    javax.xml.parsers.DocumentBuilderFactory factory =
    	        javax.xml.parsers.DocumentBuilderFactory.newInstance();
    	    factory.setNamespaceAware(true);
    	    javax.xml.parsers.DocumentBuilder builder = null;
    	    try {
    	        builder = factory.newDocumentBuilder();
    	    }
    	    catch (javax.xml.parsers.ParserConfigurationException ex) {
    	    }  
    	    org.w3c.dom.Document doc = builder.parse(is);
    	    is.close();
    	    return doc;
    	}
}
