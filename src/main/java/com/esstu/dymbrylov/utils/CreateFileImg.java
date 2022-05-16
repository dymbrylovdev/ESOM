package com.esstu.dymbrylov.utils;


import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CreateFileImg {
    public Map.Entry<Boolean, String> saveImgInFolder(String path)  {
        Map.Entry result = Map.entry(false, "");
        String parent = System.getProperty("user.dir");
        BufferedImage bImage = null;
        try {
            File initialImage = new File(path);
            String parentPath = initialImage.getParentFile().getParentFile().toString().substring(6);
            if (Objects.equals(parent, parentPath)) {
                return result = Map.entry(true, initialImage.toString().substring(6));
            };
            bImage = ImageIO.read(initialImage);
            File newFile = getUniqueFilePath(parent, "img", initialImage.getName());
            Boolean resultSave = ImageIO.write(bImage, "png", newFile);

            if (resultSave) {
                result = Map.entry(true, newFile.toString());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return result;
    }

    public static File getUniqueFilePath(String parent, String child, String fileName) {
        File dir = new File(parent, child);
        String uniqueName = getUniqueFileName(parent, child, fileName);
        return new File(dir, uniqueName);
    }

    public static String getUniqueFileName(String parent, String child, String fileName) {
        final File dir = new File(parent, child);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        int num = 0;
        final String ext = getFileExtension(fileName);
        final String name = getFileName(fileName);
        File file = new File(dir, fileName);
        while (file.exists()) { // если существует такой файл создаст новый
            num++;
            file = new File(dir, name + "-" + num + ext);
        }
        return file.getName();
    }

    public static String getFileExtension(final String path) {
        if (path != null && path.lastIndexOf('.') != -1) {
            return path.substring(path.lastIndexOf('.'));
        }
        return null;
    }


    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }


}