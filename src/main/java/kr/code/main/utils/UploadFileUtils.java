package kr.code.main.utils;

import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadFileUtils {

    // 파일이 저장되는 경로
    @Value("${file.upload.path}")
    private String FILE_PATH;

    private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

    // 파일 업로드 처리
    public String uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {

        String originalFileName = file.getOriginalFilename(); // 파일명
        byte[] fileData = file.getBytes();  // 파일 데이터

        // 1. 파일명 중복 방지 처리
        String uuidFileName = getUuidFileName(originalFileName);

        // 2. 파일 업로드 경로 설정
        String rootPath = getRootPath(originalFileName, request); // 기본경로 추출(이미지 or 일반파일)
        String datePath = getDatePath(rootPath); // 날짜 경로 추출, 날짜 폴더 생성

        // 3. 서버에 파일 저장
        File target = new File(rootPath + datePath, uuidFileName); // 파일 객체 생성
//        FileCopyUtils.copy(fileData, target); // 파일 객체에 파일 데이터 복사

        // 복사할 빈 파일 만들기
        target.createNewFile();
        // 기존 파일을 생성된 새로운 빈 파일로 복사한다.
        file.transferTo(target);

        // 4. 이미지 파일인 경우 썸네일이미지 생성
        if (MediaUtils.getMediaType(originalFileName) != null) {
            uuidFileName = makeThumbnail(rootPath, datePath, uuidFileName);
        }

        // 5. 파일 저장 경로 치환
        return replaceSavedFilePath(datePath, uuidFileName);
    }

    // 파일 삭제 처리
    public void deleteFile(String fileName, HttpServletRequest request) {

        String rootPath = getRootPath(fileName, request); // 기본경로 추출(이미지 or 일반파일)

        // 1. 원본 이미지 파일 삭제
        MediaType mediaType = MediaUtils.getMediaType(fileName);
        if (mediaType != null) {
            String originalImg = fileName.substring(0, 12) + fileName.substring(14);
            new File(rootPath + originalImg.replace('/', File.separatorChar)).delete();
        }

        // 2. 파일 삭제(썸네일이미지 or 일반파일)
        new File(rootPath + fileName.replace('/', File.separatorChar)).delete();
    }

    // 파일 출력을 위한 HttpHeader 설정
    public HttpHeaders getHttpHeaders(String fileName) throws Exception {

        MediaType mediaType = MediaUtils.getMediaType(fileName); // 파일타입 확인
        HttpHeaders httpHeaders = new HttpHeaders();

        // 이미지 파일 O
        if (mediaType != null) {
            httpHeaders.setContentType(mediaType);
            return httpHeaders;
        }

        // 이미지 파일 X
        fileName = fileName.substring(fileName.indexOf("_") + 1); // UUID 제거
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 다운로드 MIME 타입 설정
        // 파일명 한글 인코딩처리
        httpHeaders.add("Content-Disposition",
                "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),
                        "ISO-8859-1")+"\"");

        return httpHeaders;
    }

    // 기본 경로 추출
    public String getRootPath(String fileName, HttpServletRequest request) {

        MediaType mediaType = MediaUtils.getMediaType(fileName); // 파일타입 확인
        if (mediaType != null)
//            return request.getServletContext().getRealPath(FILE_PATH + "/images"); // 이미지 파일 경로
            return FILE_PATH + "/images";

//        return request.getServletContext().getRealPath(FILE_PATH + "/files"); // 일반파일 경로
        return FILE_PATH + "/files";
    }

    // 날짜 폴더명 추출
    private String getDatePath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();
        String yearPath = File.separator + calendar.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        makeDateDir(uploadPath, yearPath, monthPath, datePath);

        return datePath;
    }

    // 날짜별 폴더 생성
    private void makeDateDir(String uploadPath, String... paths) {

        // 날짜별 폴더가 이미 존재하면 메서드 종료
        if (new File(uploadPath + paths[paths.length - 1]).exists())
            return;

        uploadPath = uploadPath + paths[paths.length - 1];
        File dirPath = new File(uploadPath);
        Boolean test = dirPath.mkdirs();
        System.out.println("경로 생성 : " + dirPath + " --> " + test.toString());

//        for (String path :  paths) {
//            File dirPath = new File(uploadPath + path);
//            if (!dirPath.exists()) {
//                boolean result = dirPath.mkdirs();
//                System.out.println("mkdir is " + result);
//            }
//        }
    }

    // 파일 저장 경로 치환
    private String replaceSavedFilePath(String datePath, String fileName) {
        String savedFilePath = datePath + File.separator + fileName;
        return savedFilePath.replace(File.separatorChar, '/');
    }

    // 파일명 중복방지 처리
    private String getUuidFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    // 썸네일 이미지 생성
    private String makeThumbnail(String uploadRootPath, String datePath, String fileName) throws Exception {

        // 원본이미지를 메모리상에 로딩
        BufferedImage originalImg = ImageIO.read(new File(uploadRootPath + datePath, fileName));
        // 원본이미지를 축소
        BufferedImage thumbnailImg = Scalr.resize(originalImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
        // 썸네일 파일명
        String thumbnailImgName = "s_" + fileName;
        // 썸네일 업로드 경로
        String fullPath = uploadRootPath + datePath + File.separator + thumbnailImgName;
        // 썸네일 파일 객체생성
        File newFile = new File(fullPath);
        // 썸네일 파일 확장자 추출
        String formatName = MediaUtils.getFormatName(fileName);
        // 썸네일 파일 저장
        ImageIO.write(thumbnailImg, formatName, newFile);

        return thumbnailImgName;
    }

}