
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 文件路径处理工具
 */
public class FilePathUtils {

    public static final String DOT = ".";
    public static final String SLASH_ONE = "/";
    public static final String SLASH_TWO = "\\";

    /**
     * 获取没有扩展名的文件名
     *
     * @param fileName
     * @return
     */
    public static String getWithoutExtension(String fileName) {
        String ext = StringUtils.substring(fileName, 0,
                StringUtils.lastIndexOf(fileName, DOT));
        return StringUtils.trimToEmpty(ext);
    }

    /**
     * 获取扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT))
            return StringUtils.EMPTY;
        String ext = StringUtils.substring(fileName,
                StringUtils.lastIndexOf(fileName, DOT));
        return StringUtils.trimToEmpty(ext);
    }

    /**
     * 获取文件名称
     *
     * @param file
     * @return
     */
    public static String getFilename(String file) {
        String[] seps = getWithoutExtension(file).split(File.separator);
        return StringUtils.trimToEmpty(seps[seps.length - 1]);
    }

    /**
     * 判断是否同为扩展名
     *
     * @param fileName
     * @param ext
     * @return
     */
    public static boolean isExtension(String fileName, String ext) {
        return StringUtils.equalsIgnoreCase(getExtension(fileName), ext);
    }

    /**
     * 判断是否存在扩展名
     *
     * @param fileName
     * @return
     */
    public static boolean hasExtension(String fileName) {
        return !isExtension(fileName, StringUtils.EMPTY);
    }

    /**
     * 得到正确的扩展名
     *
     * @param ext
     * @return
     */
    public static String trimExtension(String ext) {
        return getExtension(DOT + ext);
    }

    /**
     * 向path中填充扩展名(如果没有或不同的话)
     *
     * @param fileName
     * @param ext
     * @return
     */
    public static String fillExtension(String fileName, String ext) {
        fileName = replacePath(fileName + DOT);
        ext = trimExtension(ext);
        if (!hasExtension(fileName)) {
            return fileName + getExtension(ext);
        }
        if (!isExtension(fileName, ext)) {
            return getWithoutExtension(fileName) + getExtension(ext);
        }
        return fileName;
    }

    /**
     * 判断是否是文件PATH
     *
     * @param fileName
     * @return
     */
    public static boolean isFile(String fileName) {
        return hasExtension(fileName);
    }

    /**
     * 判断是否是文件夹PATH
     *
     * @param fileName
     * @return
     */
    public static boolean isFolder(String fileName) {
        return !hasExtension(fileName);
    }

    public static String replacePath(String path) {
        return StringUtils.replace(StringUtils.trimToEmpty(path), SLASH_ONE,
                SLASH_TWO);
    }

    /**
     * 链接PATH前处理
     *
     * @param path
     * @return
     */
    public static String trimLeftPath(String path) {
        if (isFile(path))
            return path;
        path = replacePath(path);
        String top = StringUtils.left(path, 1);
        if (StringUtils.equalsIgnoreCase(SLASH_TWO, top))
            return StringUtils.substring(path, 1);
        return path;
    }

    /**
     * 链接PATH后处理
     *
     * @param path
     * @return
     */
    public static String trimRightPath(String path) {
        if (isFile(path))
            return path;
        path = replacePath(path);
        String bottom = StringUtils.right(path, 1);
        if (StringUtils.equalsIgnoreCase(SLASH_TWO, bottom))
            return StringUtils.substring(path, 0, path.length() - 2);
        return path + SLASH_TWO;
    }

    /**
     * 链接PATH前后处理，得到准确的链接PATH
     *
     * @param path
     * @return
     */
    public static String trimPath(String path) {
        path = StringUtils.replace(StringUtils.trimToEmpty(path), SLASH_ONE,
                SLASH_TWO);
        path = trimLeftPath(path);
        path = trimRightPath(path);
        return path;
    }

    /**
     * 通过数组完整链接PATH
     *
     * @param paths
     * @return
     */
    public static String bulidFullPath(String... paths) {
        StringBuffer sb = new StringBuffer();
        for (String path : paths) {
            sb.append(trimPath(path));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String fileName = "D:/apache-tomcat-7.0.23/webapps/ROOT/report rets/attachment/report/test/";
        System.out.println(FilePathUtils.getExtension("asdfasfd.PDF"));
        System.out.println(FilePathUtils.getWithoutExtension("D:/apache-tomcat-7.0.23/asdfasfd.PDF"));
        System.out.println(FilePathUtils.getFilename("D:/apache-tomcat-7.0.23/asdfasfd.PDF"));
        System.out.println(new File(fileName).getPath());
        String[] paths = {"win", "t", "a", "teswe.te"};
        System.out.println(FilePathUtils.bulidFullPath(paths));
    }

}
