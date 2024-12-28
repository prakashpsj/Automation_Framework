package com.policybazaar.components.reporting;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.XmlToken.Factory;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.xmlbeans.XmlToken.Factory;

public class CustomXWPFDocument extends XWPFDocument {

    public CustomXWPFDocument(InputStream in) throws IOException {
        super(in);
    }

    public void createPicture(String blipId, int id, int width, int height) {
        boolean EMU = true;
        width *= 9525;
        height *= 9525;
        CTInline inline = this.createParagraph().createRun().getCTR().addNewDrawing().addNewInline();

//        StringBuilder picXmlBuilder = new StringBuilder();

//        picXmlBuilder.append("<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">")
//                .append("<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">")
//                .append("<pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">")
//                .append("<pic:nvPicPr>")
//                .append("<pic:cNvPr id=\"").append(id).append("\" name=\"Generated\"/>")
//                .append("<pic:cNvPicPr/>")
//                .append("</pic:nvPicPr>")
//                .append("<pic:blipFill>")
//                .append("<a:blip r:embed=\"").append(blipId).append("\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>")
//                .append("<a:stretch>")
//                .append("<a:fillRect/>")
//                .append("</a:stretch>")
//                .append("</pic:blipFill>")
//                .append("<pic:spPr>")
//                .append("<a:xfrm>")
//                .append("<a:off x=\"0\" y=\"0\"/>")
//                .append("<a:ext cx=\"").append(width).append("\" cy=\"").append(height).append("\"/>")
//                .append("</a:xfrm>")
//                .append("<a:prstGeom prst=\"rect\">")
//                .append("<a:avLst/>")
//                .append("</a:prstGeom>")
//                .append("</pic:spPr>")
//                .append("</pic:pic>")
//                .append("</a:graphicData>")
//                .append("</a:graphic>");
//
//        String picXml = picXmlBuilder.toString();



        String picXml = "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">         <pic:nvPicPr>            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" + "            <pic:cNvPicPr/>" + "         </pic:nvPicPr>" + "         <pic:blipFill>" + "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" + "            <a:stretch>" + "               <a:fillRect/>" + "            </a:stretch>" + "         </pic:blipFill>" + "         <pic:spPr>" + "            <a:xfrm>" + "               <a:off x=\"0\" y=\"0\"/>" + "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" + "            </a:xfrm>" + "            <a:prstGeom prst=\"rect\">" + "               <a:avLst/>" + "            </a:prstGeom>" + "         </pic:spPr>" + "      </pic:pic>" + "   </a:graphicData>" + "</a:graphic>";
        XmlToken xmlToken = null;

        try {
            xmlToken = Factory.parse(picXml);
        } catch (XmlException var11) {
            var11.printStackTrace();
        }
        inline.set(xmlToken);
        inline.setDistT(0L);
        inline.setDistB(0L);
        inline.setDistL(0L);
        inline.setDistR(0L);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx((long)width);
        extent.setCy((long)height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId((long)id);
        docPr.setName("Picture" + id);
        docPr.setDescr("Generated");
    }
}





