package com.example.demo.utils.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
    	List<String> fileNameList = new ArrayList();
    	
    	multipartFiles.forEach(file -> {
    		String fileName = createFileName(file.getOriginalFilename());
    		System.out.println("fileName : " + fileName);
    		ObjectMetadata objectMetadata = new ObjectMetadata();
    		objectMetadata.setContentLength(file.getSize());
    		objectMetadata.setContentType(file.getContentType());
    		
    		try(InputStream inputStream = file.getInputStream()){
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            fileNameList.add(fileName);
    	});
    	
    	return fileNameList;
    }
    
    public String createFileName(String fileName) {
    	return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }
    
    private String getFileExtension(String fileName) {
    	try {
    		return fileName.substring(fileName.lastIndexOf("."));
    	} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
		}
    }

//    public String saveFile(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convertMultiPartToFile(multipartFile)
//                .orElseThrow(IllegalArgumentException::new);
//
//        return upload(uploadFile, dirName);
//    }
//
//    public String upload(File uploadFile, String dirName) {
//        String filename = dirName + "/" + uploadFile.getName();
//        String uploadUrl = putS3(uploadFile, filename);
//        removeNewFile(uploadFile);
//        return uploadUrl;
//    }
//
//    public String putS3(File uploadFile, String filename) {
//        amazonS3.putObject(new PutObjectRequest(bucket, filename, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3.getUrl(bucket, filename).toString();
//    }
//
//    public void removeNewFile(File targetFile) {
//        targetFile.delete();
//    }
//
//    public Optional<File> convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
//        File convertFile = new File(multipartFile.getOriginalFilename());
//
//        if(convertFile.createNewFile()) {
//
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(multipartFile.getBytes());
//            }
//
//            return Optional.of(convertFile);
//        }
//
//        return Optional.empty();
//    }
}
