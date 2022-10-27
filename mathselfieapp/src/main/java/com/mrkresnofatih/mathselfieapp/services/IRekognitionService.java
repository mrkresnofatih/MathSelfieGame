package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;

public interface IRekognitionService {
    BaseFuncResponse<String> GetEmotionOfImage(String bucketName, String key);
}
