import axios from "axios"
import { setGame } from "../store/slices/gameSlice"
import { store } from "../store/store"

const getGameAPI = (problemSetId, callback) => {
    axios({
        method: "get",
        url: `http://localhost:8080/api/v1/game/get-game?ProblemSetId=${problemSetId}`
    }).then((response) => {
        callback()
        store.dispatch(setGame(response.data.data))
        console.log("get game API finished successfully")
    })
}

export default getGameAPI