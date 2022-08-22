package com.pdf_reader.calculation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.apache.logging.log4j.LogManager;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Jana
 */
public class Container {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Container.class);
    Alert a = new Alert(null);

    int colorPg;

    public int docCount(List<File> files) {
        int count = 0;
        String status = "";
        try {

            for (File file : files) {
                if (file.getName().endsWith(".pdf")) {
                    status = "pdf";
                } else if (file.getName().endsWith(".xlsx")) {
                    status = "xcel";
                } else if (file.getName().endsWith(".docx")) {
                    status = "doc";
                } else if (file.getName().endsWith(".pptx")) {
                    status = "pptx";
                }
            }

            switch (status) {
                case "pdf":
                    count = getPdfCount(files);
                    break;
                case "xcel":
                    count = getExcelCount(files);
                    break;
                case "doc":
                    count = getWordDocCount(files);
                    break;
                case "pptx":
                    count = getPowerPoint(files);
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(Container.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int getPdfCount(List<File> file) throws IOException {
        int count = 0;
        int empty = 0;
        List<PDDocument> doc = new ArrayList<>();

        try {
            for (File f : file) {
                //store all pdf files into doc arry from file array
                doc.add(PDDocument.load(f));
            }

            for (PDDocument docu : doc) {
                //iterate pages one by one

                count += docu.getNumberOfPages();

                PDFTextStripper stripper = new PDFTextStripper();
                PDFRenderer pdfRenderer = new PDFRenderer(docu);

                Splitter splitter = new Splitter();
                List<PDDocument> pages = splitter.split(docu);
                //Creating an iterator 
                Iterator<PDDocument> iterator = pages.listIterator();
                //get docu list's document and iterate it's page one by one
                int i = 0;
                while (iterator.hasNext()) {
                    docu = iterator.next();
                    String text = stripper.getText(docu).trim();
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
                    if (isBlank(pdfRenderer.renderImage(i)) && text.length() == 0) {
                        // if there are not images in file then this line will execute
                        empty++;
                        //pageArray.add(empty);
                    }
                    i++;
                    docu.close();
                }

            }
            if (empty > 0) {
                count -= empty;
                String page = (empty == 1) ? "Sheet" : "Sheets";
                AlertType at = AlertType.WARNING;
                String msg = empty + " Empty " + page + ". Please Open File & Check It If You Want...!";
                String title = "Warning";
                setMessage(at, msg, title);
            }
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }

        for (PDDocument document : doc) {
            document.close();
        }
        return count;
    }

    public int getExcelCount(List<File> file) {

        System.out.println("EXCEL");
        int count = 0;
        FileInputStream fil;
        XSSFWorkbook workbook;
        HashSet<Integer> emp = new HashSet<>();
        for (File f : file) {
            try {
                fil = new FileInputStream(new File(f.getAbsolutePath()));
                System.out.println(f.getAbsolutePath());
                workbook = new XSSFWorkbook(fil);
                count += workbook.getNumberOfSheets();

                //Get first/desired sheet from the workbook
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    List<XSSFTable> tables = sheet.getTables();

                    if (!isSheetEmpty(workbook.getSheetAt(i)) && (tables.size()) == 0) {
                        //if, size == 0, not empty,if empty, then add empty page number
                        emp.add(i + 1);
                        //i+1, because,  i = 0. to get actual page number, i +1
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if ((emp.size()) > 0) {
            String page = (emp.size() == 1) ? "Sheet" : "Sheets";
            AlertType at = AlertType.WARNING;
            String msg = "This File Contain " + emp.size() + " Empty " + page + ". Please Open File & Check It If You Want...!";
            String title = "Warning";
            setMessage(at, msg, title);
        }
        return count;
    }//done

    public int getWordDocCount(List<File> file) throws IOException {
        int count = 0;
        FileInputStream fil;
        XWPFDocument dd;

        for (File f : file) {
            fil = new FileInputStream(new File(f.getAbsolutePath()));
            try {
                dd = new XWPFDocument(fil);
                count += dd.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Container.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }
        return count;
    }

    public int getPowerPoint(List<File> file) {
        int count = 0;
        FileInputStream fil;
        XMLSlideShow show;
        for (File f : file) {
            try {
                fil = new FileInputStream(new File(f.getAbsolutePath()));
                show = new XMLSlideShow(fil);
                List<XSLFSlide> slides = show.getSlides();

                for (XSLFSlide slide : slides) {
                    count++;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return count;
    }

    boolean isSheetEmpty(XSSFSheet sheet) {
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            XSSFRow row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            while (cells.hasNext()) {
                XSSFCell cell = (XSSFCell) cells.next();
                if (!cell.getStringCellValue().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setMessage(AlertType at, String msg, String title) {
        Alert a = new Alert(at);
        a.setContentText(msg);
        a.setTitle(title);
        a.show();
    }

    private Boolean isBlank(BufferedImage pageImage) throws IOException {
        //this mecthod check the page emptynes and is it a color or grayscale one
        BufferedImage bufferedImage = pageImage;

        long r;
        long b;
        long g;
        long pixel;
        boolean isColor = false;
        long coun = 0;
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        Double areaFactor = (width * height) * 0.99;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                Color c = new Color(bufferedImage.getRGB(x, y));
                //this line chck is there any color difference in the page
                if (c.getRed() == c.getGreen() && c.getRed() == c.getBlue() && c.getRed() >= 248) {
                    coun++;
                }
                pixel = bufferedImage.getRGB(x, y);
                r = (pixel >> 16) & 0xff;
                g = (pixel >> 8) & 0xff;
                b = (pixel) & 0xff;
                if (r != g || g != b) {
                    isColor = true;
                    break;
                }
            }
            if (isColor) {
                colorPg++;
                break;
            }

        }
        return coun >= areaFactor;
    }

    public int getColorPge() {
        return this.colorPg;
    }
}
