package org.kuali.kpme.edo;

import org.junit.Test;
import org.kuali.kpme.edo.service.AssetAggregationServiceImpl;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.Arrays;

public class PdfMergeTest {

    String[] samples={"AxiomOfChoice.pdf","Peano1stOrder.docx","RussellsParadox.pdf"};

    @Test
    public void test1() throws Exception {

//        AssetAggregationServiceImpl assetAggregationService=new AssetAggregationServiceImpl();
//
//        File outFile=new File("out.pdf");
//        AssetAggregationServiceImpl.AggregatedAssetStatus status=assetAggregationService.mergeAssets(EdoUnitTestBase.UNIT_TEST_SAMPLE_FILES_PATH, Arrays.asList(samples));
//        FileCopyUtils.copy(status.file,outFile);
//        status.file.delete();
    }

}
