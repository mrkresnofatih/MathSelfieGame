import axios from "axios"
import FormData from 'form-data';

const submitAnswerAPI = ({problemSetId, problemId, file}, callback) => {
    const formData = new FormData();
    formData.append("file", file);

    
    const url = `http://localhost:8080/api/v1/game/submit-answer?ProblemSetId=${problemSetId}&ProblemId=${problemId}`
    
    console.log("url: ", url)
    axios
    .post(url, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        },
    }).then((response) => {
        console.log("submit answer API finished successfully");
        callback();
    }).catch((err) => console.log(err))
}

export default submitAnswerAPI