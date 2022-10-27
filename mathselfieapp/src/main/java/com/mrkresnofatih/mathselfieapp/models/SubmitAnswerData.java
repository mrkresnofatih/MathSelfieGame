package com.mrkresnofatih.mathselfieapp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubmitAnswerData implements IJsonSerializable {
    private String bucketName;
    private String key;
    private String problemSetId;
    private String problemId;

    public SubmitAnswerData() {
    }

    public SubmitAnswerData(String bucketName, String key, String problemSetId, String problemId) {
        this.bucketName = bucketName;
        this.key = key;
        this.problemSetId = problemSetId;
        this.problemId = problemId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(String problemSetId) {
        this.problemSetId = problemSetId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Override
    public String toJsonSerialized() {
        try {
            var mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            return "JsonProcessingException";
        }
    }
}
