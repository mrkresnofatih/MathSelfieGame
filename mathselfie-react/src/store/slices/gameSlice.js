import { createSlice, current } from '@reduxjs/toolkit'

const initialState = {
    problemSetId: "",
    problems: [],
    problemIndex: 0
}

export const gameSlice = createSlice({
    name: 'game',
    initialState,
    reducers: {
        setGame: (state, action) => {
            const { problemSetId, problems } = action.payload;
            state.problemSetId = problemSetId;
            state.problems.push(...problems)
            console.log("GameState: ", current(state))
        },
        resetGame: () => {
            console.log("GameState: ", initialState)
            return initialState
        },
        nextProblem: (state) => {
            state.problemIndex++;
            console.log("GameState: ", current(state))
        }
    }
})

export const { setGame, resetGame, nextProblem } = gameSlice.actions;

export default gameSlice.reducer