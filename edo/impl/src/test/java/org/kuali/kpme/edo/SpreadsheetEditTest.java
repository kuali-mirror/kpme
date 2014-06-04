package org.kuali.kpme.edo;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ojb.broker.PBKey;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.core.impl.config.property.Config;
import org.kuali.rice.core.impl.config.property.Param;
import org.kuali.rice.coreservice.api.parameter.Parameter;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.sql.RowSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpreadsheetEditTest {


    enum Location {LEFT, CENTER, RIGHT};


    Pattern pattern=Pattern.compile("\\$\\{(.*?)\\}"); // e.g. ${lastName}


    interface xlsBean { // wraps Cells and Headers so they can be used interchangeably; also simplifies data types
        public void setData(String datum);
        public String getData();
    }

    Map<String,xlsBean> beans=new HashMap();

    class CellBean implements xlsBean {
        int index=1;

        HSSFCell cell;
        CellBean(HSSFCell cell) { this.cell=cell;}

        public void setData(String datum) {
            int destinationRowNumber=cell.getRow().getRowNum()+index++;
            int destinationCellNumber=cell.getColumnIndex();


            HSSFSheet sheet=cell.getRow().getSheet();
            HSSFRow destinationRow=sheet.getRow(destinationRowNumber);
            if (destinationRow==null) {
                destinationRow=sheet.createRow(destinationRowNumber);
            }
            HSSFCell destinationCell=destinationRow.getCell(destinationCellNumber);

            if (destinationCell==null) {
                destinationCell=destinationRow.createCell(destinationCellNumber);
                destinationCell.setCellType(cell.getCellType());
            }

            if (cell.getCellType()== Cell.CELL_TYPE_NUMERIC) {
                destinationCell.setCellValue(new Double(datum));
            }  else {
                destinationCell.setCellValue(datum);
            }
        }

        public String getData() {
            if (cell.getCellType()== Cell.CELL_TYPE_NUMERIC) {
                return ""+cell.getNumericCellValue();
            }  else {
                return cell.getStringCellValue();
            }
        }

    }

    class HeaderBean implements xlsBean {

        Location loc;
        HSSFHeader header;
        HeaderBean(HSSFHeader header,Location loc) { this.header=header; this.loc=loc;}
        public void setData(String datum) {
            switch (loc) {
                case LEFT:
                    header.setLeft(datum);
                    break;
                case CENTER:
                    header.setCenter(datum);
                    break;
                case RIGHT:
                    header.setRight(datum);
                    break;
            }
        }
        public String getData() {
            switch (loc) {
                case LEFT:
                    return header.getLeft();
                case CENTER:
                    return header.getCenter();
                case RIGHT:
                    return header.getRight();
            }
            return null;
        }
    }


    void apply(String var,String val) {
        System.out.println(String.format("%s=%s",var,val));
        xlsBean bean=beans.get(var);
        if (bean instanceof HeaderBean) {  // work around required for weird header font size behavior
            if (Character.isDigit(val.charAt(0))) {
                val=" "+val;
            }
        }
        bean.setData(bean.getData().replace(var,val));
    }


    @Test
    public void testAddRow() throws IOException {

        final String inFilename=EdoUnitTestBase.UNIT_TEST_SAMPLE_FILES_PATH+"file1.xls";
        final String outFilename="file1-out.xls";

        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(inFilename));
        HSSFSheet sheet = wb.getSheetAt(0);

        HSSFHeader header=sheet.getHeader();

        Matcher matcher=pattern.matcher(header.getLeft());
        while (matcher.find()) {
            String index=matcher.group();
            beans.put(index, new HeaderBean(header,Location.LEFT));
        }

        matcher=pattern.matcher(header.getCenter());
        while (matcher.find()) {
            String index=matcher.group();
            beans.put(index, new HeaderBean(header,Location.CENTER));
        }

        matcher=pattern.matcher(header.getRight());
        while (matcher.find()) {
            String index=matcher.group();
            beans.put(index, new HeaderBean(header,Location.RIGHT));
        }

        HSSFRow row1=sheet.getRow(1);

        for (int i=row1.getFirstCellNum(); i<row1.getLastCellNum(); i++ ) {
            HSSFCell nextCell=row1.getCell(i);
            beans.put(nextCell.getStringCellValue(),new CellBean(nextCell));
        }

        apply("${CampusName}", "Indiana University - Bloomington");
        apply("${Title1}","2012-13 Summary Sheet");
        apply("${Title2}","TENURE ONLY");

        apply("${Last}","Test1");
        apply("${Last}","Smith2");
        apply("${Last}","Jones3");

        apply("${First}","Tester1");
        apply("${First}","Chris2");
        apply("${First}","Billy3");

        sheet.removeRow(row1);   // clears contents
        sheet.shiftRows(2,5,-1); // gets rid of blank line

        FileOutputStream stream = new FileOutputStream(outFilename);

        wb.write(stream);
        stream.close();


    }

    /* A bit extreme but the following helps keep me from checking username & password into CVS */

    Map<String,String> readParameters(String ... inputFiles) throws Exception {

        Map<String,String> params=new HashMap<String, String>();

        // Create the XMLFilter
        XMLFilter filter = new NamespaceFilter();

        // Set the parent XMLReader on the XMLFilter
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        filter.setParent(xr);

        JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        UnmarshallerHandler unmarshallerHandler = jaxbUnmarshaller.getUnmarshallerHandler();
        filter.setContentHandler(unmarshallerHandler);

        for (String filename : inputFiles)   {
            InputSource xml = new InputSource(filename);
            filter.parse(xml);

            Config config = (Config) unmarshallerHandler.getResult();
            for (Param p : config.getParamList()) {
                params.put(p.getName(),p.getValue());
            }
        }

        return params;
    }

    public class NamespaceFilter extends XMLFilterImpl {

        private static final String NAMESPACE = "http://rice.kuali.org/core/impl/config";

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            super.endElement(NAMESPACE, localName, qName);
        }

        @Override
        public void startElement(String uri, String localName, String qName,Attributes atts) throws SAXException {
            super.startElement(NAMESPACE, localName, qName, atts);
        }

    }


    @Test
    public void testReadData() throws Exception {

        Map<String,String> params=readParameters("/opt/java/settings/edo/stg/edo-stg-config.xml","/opt/java/security/edo/stg/edo-stg-security.xml");


        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUsername(params.get("edo.datasource.username"));
        dataSource.setPassword(params.get("edo.datasource.password"));
        dataSource.setUrl(params.get("edo.datasource.url"));

        StaticApplicationContext ctx=new StaticApplicationContext();
        EdoServiceLocator esl=new EdoServiceLocator();
        esl.setApplicationContext(ctx);


        JdbcTemplate tpl=new JdbcTemplate();
        tpl.setDataSource(dataSource);


        SqlRowSet rs=tpl.queryForRowSet("select * from EDO_PT_REPORT_V");

        while (rs.next()) {
            System.out.println(rs.getObject("CANDIDATE_USERNAME").toString());
        }



        /*

        WorkAreaDaoSpringOjbImpl workAreaDao=new WorkAreaDaoSpringOjbImpl();
        workAreaDao.getPersistenceBrokerTemplate().setPbKey(pbKey);
          */


    }




}
