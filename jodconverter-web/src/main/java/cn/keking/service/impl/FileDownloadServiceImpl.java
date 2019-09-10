package cn.keking.service.impl;

import cn.keking.model.FileAttribute;
import cn.keking.service.FileDownloadService;
import cn.keking.utils.FileUtils;
import cn.keking.utils.PathUtil;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName FileCopyServiceImpl
 * @Description TODO
 * @Author like
 * @Data 2019/9/10 14:50
 * @Version 1.0
 **/
@Slf4j
@Service
public class FileDownloadServiceImpl implements FileDownloadService {

    @Value("${file.dir}")
    private String filePreviewPath;

    @Value("${fdfs.path}")
    private String fdfsPath;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public void downLoadFromHttp(String urlStr, String savePath, FileAttribute fileAttribute) throws IOException {

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = FileUtils.readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileAttribute.getName());
        log.info("【HTTP下载文件】文件存储路径{}", file.getPath());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        log.info("【HTTP下载文件】文件：{} download success", url);

        System.out.println("info:" + url + " download success");

    }

    @Override
    public void downLoadFromFastDFS(String group, String path, String savePath, FileAttribute fileAttribute, HttpServletResponse response) throws IOException {
        // 获取文件
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, new DownloadByteArray());

        FileUtils.getFile(bytes, savePath, fileAttribute.getName());

    }

    @Override
    public void downLoadFromLocal(String sourcePath, String targetPath) {

        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath + sourceFile.getName());
        try {
            FileUtils.copyFile(sourceFile, targetFile);
            log.info("文件复制成功！");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("文件复制异常！");
        }


    }
}
