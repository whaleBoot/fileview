package cn.keking.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringUtils
 * @Description TODO
 * @Author like
 * @Data 2018/9/21 10:48
 * @Version 1.0
 **/

public class PathUtil {

    /**
     * 读取字符串第i次出现特定符号的位置
     *
     * @param string
     * @param i
     * @return
     */
    public static int getCharacterPosition(String string, int i, String character) {
        //这里是获取"/"符号的位置
        // Matcher slashMatcher = Pattern.compile("/").matcher(string);
        Matcher slashMatcher = Pattern.compile(character).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == i) {
                break;
            }
        }
        return slashMatcher.start();
    }

}
