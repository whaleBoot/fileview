package cn.keking.service;

import cn.keking.model.FileAttribute;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName FileCopyService
 * @Description TODO
 * @Author like
 * @Data 2019/9/10 14:45
 * @Version 1.0
 **/

public interface FileDownloadService {

    void downLoadFromHttp(String url, String savePath, FileAttribute fileAttribute) throws IOException;

    void downLoadFromLocal(String sourcePath, String targetPath) throws IOException;

    void downLoadFromFastDFS(String group, String path, String savePath, FileAttribute fileAttribute, HttpServletResponse response) throws IOException;
}
