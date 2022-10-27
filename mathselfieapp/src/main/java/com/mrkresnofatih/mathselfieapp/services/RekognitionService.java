package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.util.List;

@Service
public class RekognitionService implements IRekognitionService {
    private final Logger logger;
    private final RekognitionClient rekognitionClient;

    @Autowired
    public RekognitionService(RekognitionClient rekognitionClient) {
        this.rekognitionClient = rekognitionClient;
        this.logger = LoggerFactory.getLogger(RekognitionService.class);
    }

    @Override
    public BaseFuncResponse<String> GetEmotionOfImage(String bucketName, String key) {
        logger.info(String.format("Start: GetEmotionOfImage w/ params: bucketName: %s & key: %s", bucketName, key));
        try {
            var detectFacesReq = DetectFacesRequest.builder()
                    .attributes(Attribute.ALL)
                    .image(Image.builder()
                            .s3Object(S3Object.builder()
                                    .bucket(bucketName)
                                    .name(key)
                                    .build())
                            .build())
                    .build();
            var detectFacesResponse = rekognitionClient.detectFaces(detectFacesReq);

            if (!detectFacesResponse.hasFaceDetails()) {
                throw new Exception("detectFace response doesn't have face details");
            }

            var firstIndex = 0;
            var faceDetail = detectFacesResponse.faceDetails().get(firstIndex);
            var emotions = faceDetail.emotions();
            var mostSignificantEmotion = _GetMostSignificantEmotion(emotions);
            logger.info(String.format("MostSignificantEmotion: %s", mostSignificantEmotion));

            logger.info("Finish: GetEmotionOfImage");
            return new BaseFuncResponse<>(mostSignificantEmotion, null);
        }
        catch (Exception e) {
            logger.error(e.toString());
            logger.error("Failed to getEmotionOfImage");
            return new BaseFuncResponse<>(null, "Failed to getEmotionOfImage");
        }
    }

    private String _GetMostSignificantEmotion(List<Emotion> emotions) {
        var bestConfidence = 0f;
        var bestIndex = 100;
        var index = -1;
        for(var emotion : emotions) {
            index++;
            if (emotion.confidence() > bestConfidence) {
                bestIndex = index;
                bestConfidence = emotion.confidence();
            }
        }

        if (bestIndex == 100) {
            return "";
        }
        return emotions.get(bestIndex).type().toString();
    }
}
