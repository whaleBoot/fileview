package cn.keking.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PdfUtils {

    private final Logger LOGGER = LoggerFactory.getLogger(PdfUtils.class);

    @Autowired
    FileUtils fileUtils;

    public List<String> pdf2jpg(String pdfFilePath, String pdfName, String url) {
        URL checkIP = null;
        try {
            checkIP = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        List<String> imageUrls = new ArrayList<>();
        Integer imageCount = fileUtils.getConvertedPdfImage(pdfFilePath);
        String imageFileSuffix = ".jpg";
        // https://8个字符  http://7个字符 从这后面开始出现的第一个/就是当前file.Dir下的根目录
        log.info("url={}", url);
        int index1 = url.indexOf("/", 8);

        String pdfFolder = pdfName.substring(0, pdfName.length() - 4);
        String urlPrefix = null;
        if (!CheckIPUtil.isIP(checkIP.getHost()) || !CheckIPUtil.internalIp(checkIP.getHost())) {
            urlPrefix = url.substring(0, index1 + 1) + "documentService/" + pdfFolder;
        } else {
            urlPrefix = url.substring(0, index1 + 1) + pdfFolder;
        }
        log.info("urlPrefix{}", urlPrefix);
        if (imageCount != null && imageCount.intValue() > 0) {
            for (int i = 0; i < imageCount; i++)
                imageUrls.add(urlPrefix + "/" + i + imageFileSuffix);
            return imageUrls;
        }
        try {
            File pdfFile = new File(pdfFilePath);
            PDDocument doc = PDDocument.load(pdfFile);
            int pageCount = doc.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(doc);

            int index = pdfFilePath.lastIndexOf(".");
            String folder = pdfFilePath.substring(0, index);

            File path = new File(folder);
            if (!path.exists()) {
                path.mkdirs();
            }
            String imageFilePath;
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                imageFilePath = folder + File.separator + pageIndex + imageFileSuffix;
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
                ImageIOUtil.writeImage(image, imageFilePath, 105);
                imageUrls.add(urlPrefix + "/" + pageIndex + imageFileSuffix);
            }
            doc.close();
            fileUtils.addConvertedPdfImage(pdfFilePath, pageCount);
        } catch (IOException e) {
            LOGGER.error("Convert pdf to jpg exception", e);
        }
        return imageUrls;
    }
}
