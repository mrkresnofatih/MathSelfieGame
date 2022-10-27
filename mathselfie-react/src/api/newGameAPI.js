import axios from "axios";
import { setGame } from "../store/slices/gameSlice";
import { store } from "../store/store";

const newGameAPI = (callback) => {
    axios({
        method: "get",
        url: "http://localhost:8080/api/v1/game/new-game",
    }).then((response) => {
        store.dispatch(setGame(response.data.data))
        console.log("new game API finished successfully")
        callback()
    })
}

export default newGameAPI;