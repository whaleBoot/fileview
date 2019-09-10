package cn.keking.config;

/**
 * @ClassName ComponetImport
 * @Description 导入FastDFS-Client组件
 * @Author like
 * @Data 2018/11/16 10:57
 * @Version 1.0
 **/

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ComponetImport {
    // 导入依赖组件
}
