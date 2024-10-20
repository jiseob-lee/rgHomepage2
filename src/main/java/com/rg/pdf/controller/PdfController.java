package com.rg.pdf.controller;

/*
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
*/

//@Controller
public class PdfController {

	/*
	private final Logger logger = LogManager.getLogger(PdfController.class);


    public static final String DEST = "/home/ubuntu/pdf/hello_world.pdf";
    public static final String STAMP = "/home/ubuntu/pdf/hello_world_stamp.pdf";
 
	@RequestMapping(value = "/makepdf.do")
	//@ResponseBody
	public void makePDF() throws IOException {
	//public ResponseEntity<byte[]> getPersonList(String json) {
		
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(DEST);
 
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
 
        // Initialize document
        Document document = new Document(pdf);
 
        //Add paragraph to the document
        document.add(new Paragraph("Hello World!"));
 
        //Close document
        document.close();
        
		//Document document = new Document(null);
		//String RESULT = "";
		//PdfWriter.getInstance(document, new FileOutputStream(RESULT));
		//document.open();
		//document.add(new Paragraph("Hello World!"));
		//document.clode();
		
	    // convert JSON to Employee 
	    //Employee emp = convertSomehow(json);

	    // generate the file
	    //PdfUtil.showHelp(emp);

	    // retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
	    //byte[] contents = ("123");

	    //HttpHeaders headers = new HttpHeaders();
	    //headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    //String filename = "output.pdf";
	    //headers.setContentDispositionFormData(filename, filename);
	    //headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    //ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
	    //return response;
	}
	

	@RequestMapping(value = "/stamppdf.do")
	//@ResponseBody
	public void stampPDF() throws DocumentException, IOException {
        PdfReader reader = new PdfReader(DEST);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(STAMP));
//        stamper.setEncryption("my-owner-password".getBytes(), "my-user-password".getBytes(),
//        		com.itextpdf.text.pdf.PdfWriter.ALLOW_PRINTING | com.itextpdf.text.pdf.PdfWriter.ALLOW_COPY, 
//        		com.itextpdf.text.pdf.PdfWriter.STANDARD_ENCRYPTION_40);

        stamper.setEncryption("reader_password".getBytes(), "permission_password".getBytes(),
        		(com.itextpdf.text.pdf.PdfWriter.ALLOW_COPY | com.itextpdf.text.pdf.PdfWriter.ALLOW_PRINTING ), 
        		com.itextpdf.text.pdf.PdfWriter.STANDARD_ENCRYPTION_128);

        stamper.close();
	}
	*/
	
}
