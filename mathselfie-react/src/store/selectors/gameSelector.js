import { useSelector } from "react-redux";

export const useGameCurrentProblem = () => useSelector((state) => {
    const problemIndex = state.game.problemId;
    return state.game.problems[problemIndex];
})

export const useGameScore = () => useSelector((state) => {
    const problems = state.game.problems;
    let score = 0;
    Object.values(problems).map(s => s.correctAnswer === s.givenAnswer).forEach(s => {
        if (s === true) {
            score++;
        }
    })
    return `${score}/${Object.values(problems).length}`
})

export const useGameAboutToEnd = () => useSelector((state) => {
    const problems = state.game.problems;
    let count = 0;
    Object.values(problems).map(s => s.givenAnswer === "NONE").forEach(s => {
        if (s === true) {
            count++;
        }
    })
    return count === 1;
})

export const useGameProblemSetId = () => useSelector((state) => {
    return state.game.problemSetId;
})