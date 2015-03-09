package fxapp01.jasperreport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * http://www.javaworld.com/article/2077801/open-source-tools/flexible-reporting-with-jasperreports-and-ibatis.html
 * @author 
 */
public class JRtest01 {
    
    public void test() throws JRException, FileNotFoundException {
        List dataList = new ArrayList();
        JasperPrint jp = fillReport(dataList);
        String PdfFileName = "reports\\monthly_sales_java_beans.pdf";
        File reportFile = new File(PdfFileName);
        FileOutputStream fio = new FileOutputStream(reportFile);
        PdfExport(jp, fio);
        String XlsFileName = "reports\\monthly_sales_java_beans.xls";
        reportFile = new File(XlsFileName);
        fio = new FileOutputStream(reportFile);
        XlsExport(jp, fio);
    }
    
    /* Helper method to create a fully populated JasperPrint object from an list of Java beans */
    private JasperPrint fillReport (List dataList) throws JRException, FileNotFoundException {

        // this map could be filled with parameters defined in the report
        Map parameters = new HashMap();

        // make sure the .jasper file (a compiled version of the .jrxml template file) exists
        String reportFileName = "reports\\templates\\monthly_sales_java_beans.jasper";
        File reportFile = new File(reportFileName);
        FileInputStream fis = new FileInputStream(reportFile);

        if (!reportFile.exists()) {
            throw new JRRuntimeException(reportFileName+" file not found.");
        }

        // load up the report
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(fis);

        // pass JRBeanCollectionDataSource (which is populated with iBATIS list) to fillReport method
        return JasperFillManager.fillReport (jasperReport, parameters,
             new JRBeanCollectionDataSource (dataList));
    }
    
    public OutputStream PdfExport(JasperPrint jasperPrint, OutputStream os) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));

        exporter.exportReport();
        
        return os;
    }

    public OutputStream XlsExport(JasperPrint jasperPrint, OutputStream os) throws JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);
        
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));

        exporter.exportReport();
        
        return os;
    }
}
