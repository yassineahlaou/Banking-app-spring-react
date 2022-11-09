package com.ahlaoujwtspring.pro.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.ahlaoujwtspring.pro.model.Payment;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service

public class InvoiceService {
	
//	Logger log = LogManager.getLogger(InvoiceService.class);
	// Path to the jrxml template
		private final String invoice_template_path = "/jasper/payment_statment.jrxml";
		private final String logo_path = "/jasper/logo.png";
		
		
	
	// OrderModel is a POJO contains all the data about the Invoice
		// Locale is used to localize the PDF file (French, English...)
	    public JasperPrint generateInvoiceFor(Payment payment) throws IOException {
	    	JasperPrint print = null;

	        // We will generate the PDF  // Create a temporary PDF file
	      //  File pdfFile = File.createTempFile("my-invoice", ".pdf");

	        // Initiate a FileOutputStream
	        try
	        {
	        	// Load the invoice jrxml template.
	            final JasperReport report = loadTemplate();
	            //System.out.println("compiled");
	            // Create parameters map.
	            final Map<String, Object> parameters = parameters(payment);
	           // System.out.println("param");

	            // Create an empty datasource.
	            
	            List <Payment> list = new ArrayList<Payment>();
	            list.add(payment);
	            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
	            
	        //   System.out.println(dataSource.getData());
	       
	            // Render the PDF file
	           // JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
	            
	           
	           print =  JasperFillManager.fillReport(report, parameters, dataSource);
	         
	          JasperExportManager.exportReportToPdfFile(print, "C:\\Users\\Sony\\git\\spring-security-jwt-demo\\spring-jwt-demo\\src\\main\\resources\\jasper\\reportExemple.pdf");
	          
	        
	            	
	            
	            
	          // return print;
	           

	        
	        }
	        catch (final Exception e)
	        {
	            System.out.println(e);
	        }
			//return pdfFile;
	       // System.out.println("yes");
	       // System.out.println(print);
			
			return print;
	    }

	    // Fill template order parametres
	    private Map<String, Object> parameters(Payment payment) {
	        final Map<String, Object> parameters = new HashMap<>();

	        parameters.put("logo", getClass().getResourceAsStream(logo_path));
	        //parameters.put("logo", logo_path);
	        parameters.put("id",  payment.getId());
	       parameters.put(JRParameter.REPORT_LOCALE, new Locale("en", "US"));
	        	//System.out.println(parameters);
	        return parameters;
	    }
		private JasperReport loadTemplate() throws JRException {
			 final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template_path);
			 
			//System.out.println(reportInputStream);
		        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

		        return JasperCompileManager.compileReport(jasperDesign);
		}

}
