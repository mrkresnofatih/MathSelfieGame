import { useSelector } from "react-redux";

export const useGameCurrentProblem = () => useSelector((state) => {
    const problemIndex = state.game.problemIndex;
    return state.game.problems[problemIndex];
})