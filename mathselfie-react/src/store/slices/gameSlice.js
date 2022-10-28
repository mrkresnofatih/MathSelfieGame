import { createSlice, current } from '@reduxjs/toolkit'

const initialState = {
    problemSetId: "",
    problems: {},
    problemId: "",
}

export const gameSlice = createSlice({
    name: 'game',
    initialState,
    reducers: {
        setGame: (state, action) => {
            const { problemSetId, problems } = action.payload;
            state.problemSetId = problemSetId;
            const map = {};
            problems.forEach(problem => {
                map[problem.problemId] = problem
            });
            state.problems = map;
            const unansweredProblems = problems.filter(s => s.givenAnswer === "NONE")
            state.problemId = unansweredProblems.length > 0 ? unansweredProblems[0].problemId : ""
            console.log("GameState: ", current(state))
        },
        resetGame: () => {
            console.log("GameState: ", initialState)
            return initialState
        },
        nextProblem: (state) => {
            state.problemId = Object.values(state.problems).filter(s => s.givenAnswer === "NONE")[0].problemId;
            console.log("GameState: ", current(state))
        }
    }
})

export const { setGame, resetGame, nextProblem } = gameSlice.actions;

export default gameSlice.reducer