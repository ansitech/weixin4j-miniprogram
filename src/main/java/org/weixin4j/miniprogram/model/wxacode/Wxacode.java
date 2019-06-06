package org.weixin4j.miniprogram.model.wxacode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 小程序码
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class Wxacode {

    private BufferedInputStream bufferedInputStream;

    /**
     * 文件输入流
     *
     * @return 文件输入流
     */
    public BufferedInputStream getBufferedInputStream() {
        return bufferedInputStream;
    }

    /**
     * 设置 文件输入流
     *
     * @param bufferedInputStream 文件输入流
     */
    public void setBufferedInputStream(BufferedInputStream bufferedInputStream) {
        this.bufferedInputStream = bufferedInputStream;
    }

    /**
     * 保存为图片
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @return 文件对象
     * @throws java.io.FileNotFoundException IO异常
     */
    public File saveToImageFile(String filePath, String fileName) throws FileNotFoundException, IOException {
        String defaultSubffix = ".jpg";
        if (fileName.contains(".")) {
            defaultSubffix = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return saveToFile(filePath, fileName, defaultSubffix);
    }

    /**
     * 保存到文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名称(不包含后缀)
     * @param subffix 文件后缀
     * @return 文件对象
     * @throws java.io.FileNotFoundException IO异常
     */
    public File saveToFile(String filePath, String fileName, String subffix) throws FileNotFoundException, IOException {
        filePath = filePath.replace("/", File.separator);
        filePath = filePath.endsWith(File.separator) ? filePath : filePath + File.separator;
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(filePath + fileName + (subffix.indexOf(".") == 0 ? subffix : "." + subffix));
        FileOutputStream out = new FileOutputStream(file);
        byte[] bs = new byte[1024];
        int len;
        while ((len = bufferedInputStream.read(bs)) != -1) {
            out.write(bs, 0, len);
        }
        return file;
    }
}
