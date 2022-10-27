package com.mrkresnofatih.mathselfieapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrkresnofatih.mathselfieapp.models.*;
import com.mrkresnofatih.mathselfieapp.services.IGameService;
import com.mrkresnofatih.mathselfieapp.services.IProblemService;
import com.mrkresnofatih.mathselfieapp.services.IRekognitionService;
import com.mrkresnofatih.mathselfieapp.services.IStorageService;
import com.mrkresnofatih.mathselfieapp.utilities.Constants;
import com.mrkresnofatih.mathselfieapp.utilities.GuidHelper;
import com.mrkresnofatih.mathselfieapp.utilities.ResponseHelper;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/game")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class GameController {
    private final IGameService gameService;
    private final IStorageService storageService;
    private final IProblemService problemService;
    private final IRekognitionService rekognitionService;
    private final Logger logger;
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${infra.aws.sqs.url}")
    private String queueUrl;

    @Autowired
    public GameController(
            IGameService gameService,
            IStorageService storageService,
            IProblemService problemService,
            IRekognitionService rekognitionService, QueueMessagingTemplate queueMessagingTemplate) {
        this.gameService = gameService;
        this.storageService = storageService;
        this.problemService = problemService;
        this.rekognitionService = rekognitionService;
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.logger = LoggerFactory.getLogger(GameController.class);
    }

    @GetMapping("/new-game")
    public ResponseEntity<BaseFuncResponse<GameResponseModel>> CreateGame() {
        var createGameResponse = gameService.CreateGame();
        if (!Objects.isNull(createGameResponse.getError())) {
            return ResponseHelper.BuildBadResponse(createGameResponse.getError());
        }
        return ResponseHelper.BuildOkResponse(createGameResponse.getData());
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<BaseFuncResponse<String>> SubmitAnswer(
            @RequestParam("File") MultipartFile multipartFile,
            @RequestParam("ProblemSetId") String problemSetId,
            @RequestParam("ProblemId") String problemId) {
        logger.info(String.format("SubmitAnswer w/ problemSetId: %s and problemId: %s", problemSetId, problemId));
        var uploadFileResponse = storageService
                .uploadFile(multipartFile, String.format("dev/%s.png", GuidHelper.GetNewGuid()));
        if (!Objects.isNull(uploadFileResponse.getError())) {
            logger.error(uploadFileResponse.getError());
            return ResponseHelper.BuildBadResponse(uploadFileResponse.getError());
        }

        var submitAnswerData = new SubmitAnswerData();
        var uploadFileResponseData = uploadFileResponse.getData();
        submitAnswerData.setBucketName(uploadFileResponseData.getBucketName());
        submitAnswerData.setKey(uploadFileResponseData.getKey());
        submitAnswerData.setProblemSetId(problemSetId);
        submitAnswerData.setProblemId(problemId);

        queueMessagingTemplate
                .send(
                        queueUrl,
                        MessageBuilder
                                .withPayload(submitAnswerData.toJsonSerialized())
                                .build());
        return ResponseHelper.BuildOkResponse(uploadFileResponse.getData().getKey());
    }

    @GetMapping("/get-game")
    public ResponseEntity<BaseFuncResponse<GameResponseModel>> GetGame(@RequestParam("ProblemSetId") String problemSetId) {
        var getProblemSetRequest = new ProblemSetGetRequestModel(problemSetId);
        var problemSetResponse = problemService.GetProblemSet(getProblemSetRequest);
        if (!Objects.isNull(problemSetResponse.getError())) {
            logger.error("Failed to get problem set");
            return ResponseHelper.BuildBadResponse("Failed to get problem set");
        }
        var problemSet = problemSetResponse.getData();
        var responseData = new GameResponseModel(problemSetId, problemSet);
        return ResponseHelper.BuildOkResponse(responseData);
    }

    @SqsListener(Constants.SQS.SQSQueueName)
    public void ProcessEventMessage(String message) {
        logger.info(String.format("Start: ProcessEventMessage w/ params: %s", message));
        var objectMapper = new ObjectMapper();
        var submitAnswerData = new SubmitAnswerData();
        try {
            submitAnswerData = objectMapper.readValue(message, SubmitAnswerData.class);
        }
        catch (JsonMappingException e) {
            logger.error("Error JsonMappingException at ProcessEventMessage!");
        }
        catch (JsonProcessingException e) {
            logger.error("Error JsonProcessingException at ProcessEventMessage");
        }

        var detectedEmotionResponse = rekognitionService
                .GetEmotionOfImage(submitAnswerData.getBucketName(), submitAnswerData.getKey());
        if (!Objects.isNull(detectedEmotionResponse.getError())) {
            logger.error("Detect emotion failed");
            return;
        }

        var emotion = detectedEmotionResponse.getData();

        var submitAnswerReq = new ProblemUpdateRequestModel();
        submitAnswerReq.setProblemSetId(submitAnswerData.getProblemSetId());
        submitAnswerReq.setProblemId(submitAnswerData.getProblemId());
        submitAnswerReq.setAnswer(emotion);

        var updateResponse = problemService.UpdateProblem(submitAnswerReq);
        if (!Objects.isNull(updateResponse.getError())) {
            logger.error("Update problem failed");
        }
    }
}
