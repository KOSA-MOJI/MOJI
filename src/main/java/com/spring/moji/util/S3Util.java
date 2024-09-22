package com.spring.moji.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3Util {
	private final AmazonS3 s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public S3Util(AmazonS3 s3Client) {
		this.s3Client=s3Client;
	}
	public String uploadFile(MultipartFile multipartFile) throws IOException {
		File file = convertMultiPartToFile(multipartFile);
		String fileName = System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
		try {
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("S3 upload failed: " + e.getMessage());
		}
		file.delete();
		return s3Client.getUrl(bucketName, fileName).toString();
	}
	public void deleteFile(String fileUrl) {
		try {
			String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
			s3Client.deleteObject(bucketName, fileName);
			System.out.println("File deleted successfully: " + fileName);
		} catch (AmazonS3Exception e) {
			System.err.println("Error deleting file: " + e.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
