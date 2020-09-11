import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HtmlToPdf {

    public static void main(String[] args) throws IOException, DocumentException {


        ITextRenderer iTextRenderer = new ITextRenderer();
//        String htmlFile = CommUtil.getHtmlFile("/test/htmltopdf/2.html");

//        iTextRenderer.setDocumentFromString(htmlFile);

//        File file = new File(HtmlToPdf.class.getClassLoader().getResource("")+"2.html");
        File file = new File("D:\\ideaworkspace\\htmltopdf\\target\\classes\\2.html");
        iTextRenderer.setDocument(file);
        ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
        fontResolver.addFont("C:/WINDOWS/Fonts/msyh.ttc",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        iTextRenderer.layout();
        OutputStream os = new FileOutputStream("D:/htmltopdf.pdf");
        iTextRenderer.createPDF(os);
        os.flush();
        os.close();
    }

}
