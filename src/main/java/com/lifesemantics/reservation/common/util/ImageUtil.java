package com.lifesemantics.reservation.common.util;

import com.lifesemantics.reservation.common.Exception.CustomException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

    public static String imageSave(String saveDir, int idx, MultipartFile saveImage)
        throws IOException {
        if (!saveImage.isEmpty()) {
            if (getImageExt(saveImage) != null) {
                saveDir = Paths.get(saveDir, getYyyyMm()).toString();
                createFolder(saveDir);

                String seq = String.valueOf(getFileSequence(saveDir));

                String saveName = seq + "_" + idx + "." + getImageExt(saveImage);
                String savePath = Paths.get(saveDir, saveName).toString();
                saveImage.transferTo(new File(savePath));
                return Paths.get(getYyyyMm(), saveName).toString();
            } else {
                throw new NullPointerException("이미지를 업로드 하지 않았습니다.");
            }
        } else {
            throw new NullPointerException("잘못된 이미지를 업로드 하셨습니다.");
        }

    }

    public static String getImageExt(MultipartFile image) {
        String contentType = image.getContentType();

        if (StringUtils.hasText(contentType)) {
            if (contentType.equals("image/png")) {
                return "png";
            } else if (contentType.equals("image/jpeg")) {
                return "jpg";
            }
        }
        return null;
    }

    public static void createFolder(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (dir.mkdirs() == false) {
                throw new CustomException("폴더 생성 중 오류 발생", 500);
            }
        }
    }

    public static String getYyyyMm() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());

        return today;
    }

    public static Integer getFileSequence(String dirName) {
        File dir = new File(dirName);
        File[] files = dir.listFiles();

        return files.length;
    }

    public static String getEncodePhotoPath(String originalPath) {
        return "/image/" + Base64Utils.encodeToString(originalPath.getBytes());
    }

    public static String getDecodePhotoPath(String encodePath) {
        return new String(Base64Utils.decode(encodePath.getBytes()));
    }
}
