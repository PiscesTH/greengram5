package com.green.greengram4.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Getter
@Component
public class MyFileUtils {
    private final String uploadPrefixPath;

    public MyFileUtils(@Value("${file.dir}") String uploadPrefixPath) {
        this.uploadPrefixPath = uploadPrefixPath;
    }

    //폴더 만들기
    public String makeFolders(String path) {
        File folder = new File(uploadPrefixPath, path);
        folder.mkdirs();    //make directory >> s 유/무 차이 -> 폴더 여러 개/한 개 만들기
        return folder.getAbsolutePath();    //절대 주소 ?
    }

    //UUID : 범용 고유 식별자
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();
    }

    //확장자 얻어오기
    public String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    //랜덤 파일명 만들기 with 확장자
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + getExt(originFileNm);
    }

    //랜덤 파일명 만들기 with 확장자 from MultipartFile
    public String getRandomFileNm(MultipartFile mf) {
        String fileNm = mf.getOriginalFilename();
        return getRandomFileNm(fileNm);
    }

}
