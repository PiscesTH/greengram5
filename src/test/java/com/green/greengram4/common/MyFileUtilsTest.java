package com.green.greengram4.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MyFileUtils.class})
@TestPropertySource(properties = {  //yaml은 객체화 안 돼서 값 설정해주는 작업 필요
        "file.dir = D:/home/download"
})
class MyFileUtilsTest {

    @Autowired
    private MyFileUtils myFileUtils;

    @Test
    void makeFolders() {
        String path = "/ggg";
        File preFolder = new File(myFileUtils.getUploadPrefixPath(), path); //[ 첫번째 파라미터/두번째 파라미터 ] 형식으로 경로 지정
        assertFalse(preFolder.exists());

        String newPath = myFileUtils.makeFolders(path);
        File newFolder = new File(newPath);
        assertTrue(newFolder.exists());
        assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
    }
    @Test
    void getRandomFileNmTest() {
        String fileNm = myFileUtils.getRandomFileNm();
        System.out.println("fileNm : " +fileNm);
        assertNotNull(fileNm);
    }

    @Test
    void getExtTest() {
        String fileNm = "abc.efg.eee.jpg";
        String ext = myFileUtils.getExt(fileNm);
        assertEquals(".jpg", ext);

        String fileNm2 = "abc.efg.pngs";
        String ext2 = myFileUtils.getExt(fileNm2);
        assertEquals(".pngs", ext2);
    }
    @Test
    public void getRandomFileNm2() {
        String fileNm1 = "hello.T.jpg";
        String rFileNm1 = myFileUtils.getRandomFileNm(fileNm1);
        System.out.println("rFileNm1 : " + rFileNm1);

        String fileNm2 = "bye.Y.jpeg";
        String rFileNm2 = myFileUtils.getRandomFileNm(fileNm2);
        System.out.println("rFileNm2 : " + rFileNm2);
    }
}