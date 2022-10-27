package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import com.mrkresnofatih.mathselfieapp.models.GameResponseModel;
import com.mrkresnofatih.mathselfieapp.models.ProblemSaveRequestModel;
import com.mrkresnofatih.mathselfieapp.utilities.GuidHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService implements IGameService {
    private final IProblemService problemService;
    private final Logger logger;

    @Autowired
    public GameService(IProblemService problemService) {
        this.problemService = problemService;
        this.logger = LoggerFactory.getLogger(GameService.class);
    }

    @Override
    public BaseFuncResponse<GameResponseModel> CreateGame() {
        logger.info("Start: CreateGame");
        try {
            var createGameResponse = new GameResponseModel();
            createGameResponse.setProblems(new ArrayList<>());
            var createGameData = _CreateProblemDrafts();
            for(var createReq : createGameData) {
                var createResponse = problemService.SaveProblem(createReq);
                if (!Objects.isNull(createResponse.getError())) {
                    return new BaseFuncResponse<>(null, "create game error");
                }
                var problemGetResponse = createResponse.getData();
                createGameResponse.setProblemSetId(problemGetResponse.getProblemSetId());
                createGameResponse.addProblem(problemGetResponse);
            }
            return new BaseFuncResponse<>(createGameResponse, null);
        }
        catch (Exception e) {
            logger.error("Error at create-game " + e);
            return new BaseFuncResponse<>(null, "Error at create-game");
        }
    }

    private List<ProblemSaveRequestModel> _CreateProblemDrafts() {
        var saveRequestModelArrayList = new ArrayList<ProblemSaveRequestModel>();
        var problemSetId = GuidHelper.GetNewGuid();
        for(var i = 0; i < 5; i++) {
            var gameData = new ProblemSaveRequestModel();

            gameData.setProblemSetId(problemSetId);
            gameData.setProblemId(GuidHelper.GetNewGuid());

            var firstValue = (int)(100 + (Math.random() * 400));
            var secondValue = (int)(10 + (Math.random() * 15));
            gameData.setFirstNumber(firstValue);
            gameData.setSecondNumber(secondValue);

            var operation = _GetOperation();
            gameData.setOperation(operation);

            var correctAnswerValue = _GetCorrectAnswer(firstValue, secondValue, operation);
            var correctAnswerKey = _GetOptionKey();
            var options = _GetOptions(correctAnswerValue, correctAnswerKey);
            gameData.setCorrectAnswer(correctAnswerKey);
            gameData.setOptions(options);

            saveRequestModelArrayList.add(gameData);
        }
        return saveRequestModelArrayList;
    }

    private String _GetOperation() {
        var random = (int) (Math.random()*100);
        return switch (random % 4) {
            case 1 -> "+";
            case 2 -> "-";
            case 3 -> "x";
            default -> "%";
        };
    }

    private String _GetOptionKey() {
        var random = (int) (Math.random()*100);
        return switch (random % 4) {
            case 1 -> "HAPPY";
            case 2 -> "SAD";
            case 3 -> "ANGRY";
            default -> "SURPRISED";
        };
    }

    private int _GetCorrectAnswer(int firstValue, int secondValue, String operation) {
        return switch (operation) {
            case "+" -> firstValue + secondValue;
            case "-" -> firstValue - secondValue;
            case "x" -> firstValue * secondValue;
            default -> firstValue % secondValue;
        };
    }

    private Map<String, Integer> _GetOptions(int correctAnswer, String correctAnswerKey) {
        var returnMap = new HashMap<String, Integer>();
        List<String> optionsKeys = List.of("HAPPY", "SAD", "ANGRY", "SURPRISED");
        for(var key : optionsKeys) {
            var randomValue = (int)(Math.random() * 2000);
            returnMap.put(key, key == correctAnswerKey ? correctAnswer : randomValue);
        }
        return returnMap;
    }
}
