package org.kuali.kpme.edo.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class AssetAggregationServiceImpl {

    /*

    It may be desirable to re-write this by borrowing liberally from the PDFMergerUtility source code.
    (see http://svn.apache.org/repos/asf/pdfbox/trunk/pdfbox/src/main/java/org/apache/pdfbox/util/PDFMergerUtility.java)

    Pros:
        Wouldn't have to create intermediate temporary files
        Better control over error handling e.g. could ignore PDFs that cannot be processed
    Cons:
        It's fairly complicated
     */

    public class AggregatedAssetStatus {
        public File file;
        public Collection<String> skippedFiles;
        public AggregatedAssetStatus() {
            skippedFiles=new ArrayList<String>();
        }
    }

    public AggregatedAssetStatus mergeAssets(String root,Collection<String> filenames) throws Exception {
        Collection<String> files=new ArrayList<String>();
        for (String file : filenames) {
            files.add(root+file);
        }
        return mergeAssets(files);
    }

    public AggregatedAssetStatus mergeAssets(Collection<String> filenames) throws Exception {


        AggregatedAssetStatus status=new AggregatedAssetStatus();
        status.file=File.createTempFile("merged",".pdf");
        Collection<File> filesToDelete=new ArrayList<File>();
        PDFMergerUtility merger=new PDFMergerUtility();
        merger.setDestinationFileName(status.file.getAbsolutePath());

        for (String filename : filenames) {

            boolean canConvert=filename.toLowerCase().endsWith(".pdf");

            PDDocument document = new PDDocument();
            PDPage coverPage = new PDPage();
            document.addPage( coverPage );

            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contentStream = new PDPageContentStream(document, coverPage);

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(100, 700);
            if (canConvert) {
                contentStream.drawString("eDossier generated cover page for: " + new File(filename).getName());
            } else {
                contentStream.drawString("eDossier placeholder for unsupported file type : " + new File(filename).getName());
            }
            contentStream.endText();

            contentStream.close();

            File temp= File.createTempFile("cover",".pdf");
            filesToDelete.add(temp);
            document.save(temp.getAbsolutePath());
            document.close();


            merger.addSource(temp.getAbsolutePath());
            if (canConvert) {
                merger.addSource(filename);
            } else {
                status.skippedFiles.add(filename);
            }
        }

        merger.mergeDocuments();

        for (File cleanupFile : filesToDelete) {
            cleanupFile.delete();
        }

        return status;
    }

}
