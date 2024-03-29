package com.kuit.conet.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kuit.conet.common.exception.BaseException;
import com.kuit.conet.common.exception.StorageException;
import com.kuit.conet.domain.storage.StorageDomain;
import com.kuit.conet.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST;
import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.INVALID_FILE_EXTENSION;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {
    private static final String SPLITER = "/";
    private static int sequenceNum = 1;
    private final MemberRepository memberRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Autowired
    private AmazonS3Client amazonS3Client;

    public static String getFileName(MultipartFile file, StorageDomain storage) {
        String fileName = sequenceNum + "-" + storage.getStorage() + "Image-" + LocalDateTime.now();
        fileName = fileName.replace(" ", "-").replace(":", "-").replace(".", "-") + ".";

        String extension = null;
        try {
            //log.info("file content type: {}", file.getContentType());
            String contentType = file.getContentType();
            if (!contentType.startsWith("image")) {
                // content type 검사
                log.error("파일이 이미지 형식이 아닙니다.");
                throw new StorageException(INVALID_FILE_EXTENSION);
            }

            // 확장자명 설정
            extension = contentType.split(SPLITER)[1];
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        if (extension == null) {
            log.warn("파일의 형식이 존재하지 않습니다. 임의로 image/png 타입으로 설정합니다.");
            extension = "png";
        }
        fileName += extension;

        sequenceNum++;
        return fileName;
    }

    public String uploadToS3(MultipartFile file, String fileName) {
        long size = file.getSize(); // 파일 크기
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(size);

        try {
            // S3에 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new BaseException(BAD_REQUEST, e.getMessage());
        }

        // URL 가져오기
        String imgUrl = amazonS3Client.getUrl(bucketName, fileName).toString();
        log.info("AWS S3에 저장된 이미지 파일의 url: {}", imgUrl);

        return imgUrl;
    }

    public void deleteImage(String fileName) {
        if (!isExistImage(fileName)) {
            log.info("The file \"{}\" is not exist.", fileName);
            return;
        }

        //log.info("기존 이미지 객체를 삭제합니다.");
        log.info("delete the file: {}", fileName);
        try {
            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (AmazonServiceException e) {
            throw new StorageException(BAD_REQUEST, e.getMessage());
        }
    }

    public String getFileNameFromUrl(String url) {
        return url.split(SPLITER)[3];
    }

    public Boolean isExistImage(String fileName) {
        return amazonS3Client.doesObjectExist(bucketName, fileName);
    }

    public void deletePreviousImage(Long memberId) {
        String imgUrl = memberRepository.getImgUrlResponse(memberId).getImgUrl();
        String deleteFileName = getFileNameFromUrl(imgUrl);

        // 유저의 프로필 이미지가 기본 프로필 이미지인지 확인 -> 기본 이미지가 아니면 기존 이미지를 S3에서 이미지 삭제
        if (!memberRepository.isDefaultImage(memberId)) {
            // S3 버킷에 존재하지 않는 객체인 경우 삭제를 생략
            deleteImage(deleteFileName);
        }
    }
}