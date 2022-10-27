import { createSlice, current } from "@reduxjs/toolkit";
import routes from '../../constants/routes'

const initialState = {
    page: routes.HOME,
}

export const appSlice = createSlice({
    name: 'app',
    initialState,
    reducers: {
        goToHomePage: (state) => {
            state.page = routes.HOME
            console.log("AppState: ", current(state))
        },
        goToGamePage: (state) => {
            state.page = routes.GAME
            console.log("AppState: ", current(state))
        }
    }
})

export const { goToGamePage, goToHomePage } = appSlice.actions;

export default appSlice.reducer