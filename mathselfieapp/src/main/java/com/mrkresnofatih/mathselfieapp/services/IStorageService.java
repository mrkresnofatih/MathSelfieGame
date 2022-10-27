package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import com.mrkresnofatih.mathselfieapp.models.StorageAddressDetail;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    BaseFuncResponse<StorageAddressDetail> uploadFile(MultipartFile multipartFile, String location);
}
