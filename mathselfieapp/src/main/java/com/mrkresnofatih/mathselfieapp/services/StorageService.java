package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import com.mrkresnofatih.mathselfieapp.models.StorageAddressDetail;
import com.mrkresnofatih.mathselfieapp.utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
public class StorageService implements IStorageService {
    private final S3Client s3Client;
    private final Logger logger;

    @Autowired
    public StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
        this.logger = LoggerFactory.getLogger(StorageService.class);
    }

    @Override
    public BaseFuncResponse<StorageAddressDetail> uploadFile(MultipartFile multipartFile, String location) {
        logger.info(String.format("Start: uploadFile w/ param: %s", location));
        try {
            var putObjectReq = PutObjectRequest.builder()
                    .bucket(Constants.S3.S3BucketName)
                    .key(location)
                    .build();
            s3Client.putObject(
                    putObjectReq,
                    RequestBody
                            .fromInputStream(
                                    multipartFile.getInputStream(),
                                    multipartFile.getSize()
                            ));
            logger.info("Finish: uploadFile");
            return new BaseFuncResponse<>(
                    new StorageAddressDetail(Constants.S3.S3BucketName, location),
                    null);

        }
        catch (S3Exception e) {
            logger.error("Error at uploadFile");
            logger.error(e.toString());
            return new BaseFuncResponse<>(null, "Error at uploadFile");
        } catch (IOException e) {
            logger.error("IO Error at uploadFile");
            return new BaseFuncResponse<>(null, "IO Error at uploadFile");
        }
    }
}
