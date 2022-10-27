import { configureStore } from '@reduxjs/toolkit'
import gameReducer from './slices/gameSlice'
import appReducer from './slices/appSlice'


export const store = configureStore({
    reducer: {
        game: gameReducer,
        app: appReducer
    },
})