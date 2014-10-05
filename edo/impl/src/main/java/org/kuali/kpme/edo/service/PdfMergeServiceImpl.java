package org.kuali.kpme.edo.service;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class PdfMergeServiceImpl implements PdfMergeService{

    @Override
    public void generateMergedPDF(OutputStream oos,Collection<String> ids) throws IOException, COSVisitorException {
        Collection<PDDocument> pdfs = new ArrayList<PDDocument>();
        for (String id: ids) {
            //pdfs.add(new COSDocument(new File("/tmp/")));
        }
        pdfMerge(oos,pdfs);
    }

    public void pdfMerge(OutputStream oos,Collection<PDDocument> pdfs) throws IOException, COSVisitorException {


        PDDocument resultingPDF = new PDDocument();
        PDFMergerUtility pmu = new PDFMergerUtility();
        for (PDDocument pdf : pdfs) {
            pmu.appendDocument(resultingPDF,pdf);
        }
        resultingPDF.save(oos);
    }
}
