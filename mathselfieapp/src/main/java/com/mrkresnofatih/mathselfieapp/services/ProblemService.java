package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.exceptions.RecordNotFoundException;
import com.mrkresnofatih.mathselfieapp.models.*;
import com.mrkresnofatih.mathselfieapp.utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProblemService implements IProblemService {
    private final DynamoDbClient dynamoDbClient;
    private final Logger logger;

    @Autowired
    public ProblemService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
        this.logger = LoggerFactory.getLogger(ProblemService.class);
    }

    private DynamoDbTable<ProblemEntity> _GetProblemEntityTable() {
        var enhancedDynamoDbClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        return enhancedDynamoDbClient.table(Constants.DynamoDb.DynamoDbTableName, TableSchema.fromBean(ProblemEntity.class));
    }

    private String _GetProblemEntityPartitionKey(String problemSetId) {
        return String.format(Constants.DynamoDb.DynamoDbKeyFormats.ProblemPartitionKey, problemSetId);
    }

    private String _GetProblemEntitySortKey(String problemId) {
        return String.format(Constants.DynamoDb.DynamoDbKeyFormats.ProblemSortKey, problemId);
    }

    @Override
    public BaseFuncResponse<ProblemGetResponseModel> SaveProblem(ProblemSaveRequestModel saveProblemRequest) {
        logger.info(String.format("Start: SaveProblem w/ param: %s", saveProblemRequest.toJsonSerialized()));
        try {
            var table = _GetProblemEntityTable();
            var newProblem = new ProblemEntity();
            newProblem.setProblemSetId(saveProblemRequest.getProblemSetId());
            newProblem.setProblemId(saveProblemRequest.getProblemId());
            newProblem.setFirstNumber(saveProblemRequest.getFirstNumber());
            newProblem.setSecondNumber(saveProblemRequest.getSecondNumber());
            newProblem.setOperation(saveProblemRequest.getOperation());
            newProblem.setOptions(saveProblemRequest.getOptions());
            newProblem.setCorrectAnswer(saveProblemRequest.getCorrectAnswer());
            newProblem.setGivenAnswer("NONE");

            table.putItem(newProblem);
            var returnProblem = new ProblemGetResponseModel(newProblem);
            logger.info("Finish: SaveProblem");
            return new BaseFuncResponse<>(returnProblem, null);
        }
        catch (DynamoDbException e) {
            logger.error("DynamoDbError at SaveProblem");
            return new BaseFuncResponse<>(null, "DynamoDB error");
        }
    }

    @Override
    public BaseFuncResponse<ProblemGetResponseModel> UpdateProblem(ProblemUpdateRequestModel updateProblemRequest) {
        logger.info(String.format("Start: UpdateProblem w/ params: %s", updateProblemRequest.toJsonSerialized()));
        try {
            var table = _GetProblemEntityTable();
            var partitionKey = _GetProblemEntityPartitionKey(updateProblemRequest.getProblemSetId());
            var sortKey = _GetProblemEntitySortKey(updateProblemRequest.getProblemId());
            var dynamoDbKey = Key.builder()
                    .partitionValue(partitionKey)
                    .sortValue(sortKey)
                    .build();
            var foundProblem = table.getItem(r -> r.key(dynamoDbKey));
            if (Objects.isNull(foundProblem)) {
                throw new RecordNotFoundException();
            }
            foundProblem.setGivenAnswer(updateProblemRequest.getAnswer());
            table.updateItem(r -> r.item(foundProblem));
            var returnProblem = new ProblemGetResponseModel(foundProblem);
            logger.info("Finish: UpdateProblem");
            return new BaseFuncResponse<>(returnProblem, null);
        }
        catch (DynamoDbException e) {
            logger.error("DynamoDbError at UpdateProblem");
            return new BaseFuncResponse<>(null, "DynamoDB error");
        } catch (RecordNotFoundException e) {
            logger.error("RecordNotFound error at UpdateProblem");
            return new BaseFuncResponse<>(null, "RecordNotFound error");
        }
    }

    @Override
    public BaseFuncResponse<List<ProblemGetResponseModel>> GetProblemSet(ProblemSetGetRequestModel getProblemSetRequest) {
        logger.info(String.format("Start: GetProblemSet w/ param: %s", getProblemSetRequest.toJsonSerialized()));
        try {
            var table = _GetProblemEntityTable();
            var queryStartKey = Key.builder()
                    .partitionValue(_GetProblemEntityPartitionKey(getProblemSetRequest.getProblemSetId()))
                    .sortValue(_GetProblemEntitySortKey(""))
                    .build();
            var queryRequest = QueryEnhancedRequest.builder()
                    .limit(20)
                    .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(queryStartKey))
                    .build();
            var results = table
                    .query(queryRequest)
                    .items()
                    .iterator();
            ProblemGetResponseModel problemGetResponse;
            var problemList = new ArrayList<ProblemGetResponseModel>();
            while (results.hasNext()) {
                problemGetResponse = new ProblemGetResponseModel();
                var fromTable = results.next();
                problemGetResponse.setProblemSetId(fromTable.getProblemSetId());
                problemGetResponse.setProblemId(fromTable.getProblemId());
                problemGetResponse.setCorrectAnswer(fromTable.getCorrectAnswer());
                problemGetResponse.setOptions(fromTable.getOptions());
                problemGetResponse.setOperation(fromTable.getOperation());
                problemGetResponse.setGivenAnswer(fromTable.getGivenAnswer());
                problemGetResponse.setFirstNumber(fromTable.getFirstNumber());
                problemGetResponse.setSecondNumber(fromTable.getSecondNumber());

                problemList.add(problemGetResponse);
            }
            logger.info("Finish: GetProblemSet");
            return new BaseFuncResponse<>(problemList, null);
        }
        catch (DynamoDbException e) {
            logger.error("DynamoDb error at getProblemSet");
            return new BaseFuncResponse<>(null, "DynamoDB error");
        }
    }
}
