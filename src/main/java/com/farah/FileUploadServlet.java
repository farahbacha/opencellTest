package com.farah;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.Date;
import java.util.logging.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.servlet.annotation.*;


@WebServlet(name = "FileUploadServlet", urlPatterns = { "fileuploadservlet" })
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FileUploadServlet extends HttpServlet {

	
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		    response.getWriter().print("The file uploaded sucessfully.");
		  }
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

   
   Part filePart = request.getPart("file");
   String fileName = getFilename(filePart);

   int index = fileName.lastIndexOf('.');
   String extension = "";
   if (index > 0) {
	   extension =fileName.substring(index + 1);
        fileName = fileName.substring(0, index);
   }
   String[] fileNameArray = fileName.split("_");
   if(fileNameArray.length != 8) {
	   filePart.write("C:\\rejet\\"+fileName+"."+extension);
	   throw new BaseException("1.Le nom du fichier doit contenir 8 éléments." ,fileNameArray[1],fileName+"."+extension,"REJECTED","ERR_CHAR_001");
	   
   }
   	
   if(fileNameArray[5].length()<5 || Integer.parseInt(fileNameArray[5]) <1) 
   {
	   filePart.write("C:\\rejet\\"+fileName+"."+extension);
	   throw new BaseException("2.Num-seq doit être supérieur à 00001.",fileNameArray[1],fileName+"."+extension,"REJECTED","ERR_CHAR_002");
	   
   }
   	
   
   if((fileNameArray[6].length()<5 || Integer.parseInt(fileNameArray[6]) <1)|| Integer.parseInt(fileNameArray[6]) > Integer.parseInt(fileNameArray[7]))
   {
	   filePart.write("C:\\rejet\\"+fileName+"."+extension);
	   throw new BaseException("3.XXXXX doit être supérieur à 00001 et inférieur ou égale à YYYYY.",fileNameArray[1],fileName+"."+extension,"REJECTED","ERR_CHAR_003");
	   
   }
   	if(fileNameArray[7].length()<5 || Integer.parseInt(fileNameArray[7]) <1) 
    {
 	   throw new BaseException("1.	YYYYY doit être >= à 00001",fileNameArray[1],fileName+"."+extension,"REJECTED","ERR_CHAR_004");
 	   
    }

   	
   	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse((InputStream)filePart.getInputStream());

           doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("Version_XSD");
        Element el = (Element)list.item(0);
        if(el != null) {
        //System.out.println(el.getTextContent());
    	System.out.println("passe a la vérification du contenu. (optionnel)");
        }
        else 
        {
        	System.out.println("khraa");
        final Logger LOGGER = Logger.getLogger("ERR_CHAR_200  Balise XSD introuvable");
        
        Handler fileHandler = new FileHandler("C:\\rejet\\"+fileName+".log");
        LOGGER.addHandler(fileHandler);
 
        		LOGGER.log(Level.WARNING, "Finer logged");

        fileHandler.setLevel(Level.ALL);

    } }catch ( IOException e) {
        e.printStackTrace();
    } catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    
   	
   	response.getWriter().print("The file uploaded sucessfully.");
 }
 
 private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	      if (cd.trim().startsWith("filename")) {
	        String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	        return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	      }
	    }
	    return null;
 }
 
 

}