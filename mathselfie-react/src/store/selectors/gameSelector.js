import { useSelector } from "react-redux";

export const useGameCurrentProblem = () => useSelector((state) => {
    const problemIndex = state.game.problemIndex;
    return state.game.problems[problemIndex];
})

export const useGameScore = () => useSelector((state) => {
    const problems = state.game.problems;
    let score = 0;
    problems.map(s => s.correctAnswer === s.givenAnswer).forEach(s => {
        if (s === true) {
            score++;
        }
    })
    return `${score}/${problems.length}`
})

export const useGameAboutToEnd = () => useSelector((state) => {
    return state.game.problemIndex === (state.game.problems.length - 1)
})