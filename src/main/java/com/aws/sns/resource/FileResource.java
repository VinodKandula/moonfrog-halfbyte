/**
 * 
 */
package com.aws.sns.resource;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * http://examples.javacodegeeks.com/enterprise-java/rest/jax-rs-download-file/
 *
 */

@Path("/files")
public class FileResource {

    // The file paths of the files in the server

    private static final String TXT_FILE = "C:\\Users\\nikos\\Desktop\\TEST_FILES\\test.txt";

    private static final String IMAGE_FILE = "C:\\Users\\nikos\\Desktop\\TEST_FILES\\test.png";

    private static final String PDF_FILE = "C:\\Users\\nikos\\Desktop\\TEST_FILES\\test.pdf";

    private static final String EXCEL_FILE = "C:\\Users\\nikos\\Desktop\\TEST_FILES\\test.xls";

    /**
     *  Download Text File
     */
    @GET
    @Path("/txt")
    @Produces("text/plain")
    public Response getTextFile() {
        File file = new File(TXT_FILE);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"test_text_file.txt\"");
        return response.build();
    }
    /**
     *  Download Image File
     **/
    @GET
    @Path("/images")
    @Produces("image/png")
    public Response getImageFile() {
        File file = new File(IMAGE_FILE);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"test_image_file.png\"");
        return response.build();
    }
    /**
     *  Download PDF File
     */
    @GET
    @Path("/pdf")
    @Produces("application/pdf")
    public Response getPDF() {
        File file = new File(PDF_FILE);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"test_PDF_file.pdf\"");
        return response.build();
    }
    /**
     *  Download Excel File
     */
    @GET
    @Path("/excel")
    @Produces("aapplication/vnd.ms-excel")
    public Response getExcell() {
        File file = new File(EXCEL_FILE);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"test_excel_file.xls\"");
        return response.build();
    }

 
}