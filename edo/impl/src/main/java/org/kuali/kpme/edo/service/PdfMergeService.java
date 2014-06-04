package org.kuali.kpme.edo.service;

import java.io.OutputStream;
import java.util.Collection;

public interface PdfMergeService {
    public void generateMergedPDF(OutputStream oos, Collection<String> ids) throws Exception;
}
