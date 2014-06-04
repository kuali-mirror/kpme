package org.kuali.kpme.edo.document;

import org.kuali.kpme.edo.workflow.DossierProcessDocumentHeader;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/28/13
 * Time: 4:03 PM
 */
public class PromotionSupplementalProcessDocument implements Serializable, SupplementalProcessDocument {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5048536736262522905L;

	public static final String TENURE_PROCESS_DOCUMENT_TYPE = "PromotionSupplementalProcessDocument";

    private DossierProcessDocumentHeader documentHeader;

    public PromotionSupplementalProcessDocument(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

    public DossierProcessDocumentHeader getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(DossierProcessDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

    /*public SplitResult findNotifyBranches(Document document) throws XPathExpressionException {
        List<String> result = new ArrayList<String>();

        Element root = document.getDocumentElement();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String xpathExpr =  "/documentContent/applicationContent/authorizedNodes/authorizedNode";
        NodeList nodes = (NodeList) xpath.compile(xpathExpr).evaluate(root, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            result.add(nodes.item(i).getFirstChild().getNodeValue());
        }

        return new SplitResult(result);
    }*/
   
}
