import { css } from '@emotion/css'
import React, { useCallback, useEffect, useRef, useState } from 'react'
import Header from '../common/Header'
import happyIcon from '../assets/emoji-happy.png'
import sadIcon from '../assets/emoji-sad.png'
import angryIcon from '../assets/emoji-angry.png'
import surprisedIcon from '../assets/emoji-surprised.png'
import Countdown from 'react-countdown'
import { useGameAboutToEnd, useGameCurrentProblem, useGameScore } from '../store/selectors/gameSelector'
import Webcam from 'react-webcam'
import { useDispatch } from 'react-redux'
import { nextProblem } from '../store/slices/gameSlice'
import { goToScorePage } from '../store/slices/appSlice'
import submitAnswerAPI from '../api/submitAnswerAPI'
import dataurlToBlob from 'dataurl-to-blob'

const Game = () => {
    const webcamRef = useRef(null)
    const currentProblem = useGameCurrentProblem()
    const currentScore = useGameScore()
    const gameAboutToEnd = useGameAboutToEnd()

    const dispatch = useDispatch()
    const getProblemFormatter = (currentProblem) => `${currentProblem.firstNumber} ${currentProblem.operation} ${currentProblem.secondNumber} = ?`
    
    const [answerMode, setAnswerMode] = useState(false);

    const capture = useCallback(
        () => {
            const imageSrc = webcamRef.current.getScreenshot();
            submitAnswerAPI({
                problemSetId: currentProblem.problemSetId,
                problemId: currentProblem.problemId,
                file: dataurlToBlob(imageSrc)
            }, () => {})
        },
        // eslint-disable-next-line
        [webcamRef, currentProblem]
    );

    useEffect(() => {
        console.log("Currentproblem: ", currentProblem)
    }, [currentProblem])
    return (
        <div className={gameStyles.body}>
            <Header/>
            {answerMode ? (
                <>
                    <TextBoard>
                        <p>{getProblemFormatter(currentProblem)}</p>
                    </TextBoard>
                    <TextBoard>
                    <CountdownTimer
                        onComplete={() => {
                            capture();
                            if (gameAboutToEnd) {
                                dispatch(goToScorePage());
                            } else {
                                setAnswerMode(i => !i);
                                console.log("CurrentScore: ", currentScore);
                                console.log("has game ended: ", gameAboutToEnd);
                                dispatch(nextProblem());
                            }
                        }}
                    />
                    </TextBoard>
                </>
            ): (
                <>
                    <TextBoard>
                    <CountdownTimer
                        onComplete={() => setAnswerMode(i => !i)}
                    />
                    </TextBoard>
                    <TextBoard>
                        <p>Please Wait ...</p>
                    </TextBoard>
                </>
            )}
            <div style={{flex: 1}}>
                <Webcam 
                    videoConstraints={{width: 300, height: 500, facingMode: "user"}}
                    screenshotFormat="blob"
                    ref={webcamRef}
                />
            </div>
            <PanelBoard
                happy={answerMode ? currentProblem.options.HAPPY : ""}
                sad={answerMode ? currentProblem.options.SAD : ""}
                angry={answerMode ? currentProblem.options.ANGRY : ""}
                surprised={answerMode ? currentProblem.options.SURPRISED : ""}
            />
        </div>
    )
}

export default Game

const gameStyles = {
    body: css`
        background-color: #06283D;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
    `
}

const CountdownTimer = ({onComplete}) => {
    return (
        <Countdown 
            date={Date.now() + 5000} 
            onComplete={onComplete}
        />
    )
}

const TextBoard = ({children}) => {
    return (
        <div className={countdownTimerStyles.body}>
            <div className={countdownTimerStyles.innerBody}>
                {children}
            </div>
        </div>
    )
}

const countdownTimerStyles = {
    body: css`
        display: flex;
        justify-content: center;
        background-color: #47B5FF;
        width: 100%;
        `,
    innerBody: css`
        width: 100%;
        border: 3px solid #06283D;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding: 18px;
        color: white;
        font-size: 18px;
        font-weight: 600;
    `
}



const PanelBoard = ({happy, sad, angry, surprised}) => {
    return (
        <>
            <div className={panelBoardStyles.body}>
                <Panel icon={happyIcon} value={happy} />
                <Panel icon={sadIcon} value={sad} />
            </div>
            <div className={panelBoardStyles.body}>
                <Panel icon={angryIcon} value={angry} />
                <Panel icon={surprisedIcon} value={surprised} />
            </div>
        </>
    )
}

const panelBoardStyles = {
    body: css`
        display: flex;
        width: 100%;
    `,
    innerbody: css`
        display: flex;
    `
}

const Panel = ({icon, value}) => {
    return (
        <div className={panelStyles.body}>
            <img src={icon} alt="" className={panelStyles.panelIcon} />
            <p className={panelStyles.panelValue}>{value}</p>
        </div>
    )
}

const panelStyles = {
    body: css`
        display: flex;
        padding: 18px;
        justify-content: center;
        align-items: center;
        background-color: #47B5FF;
        flex: 1;
        border: 3px solid #06283D;

        :hover {
            cursor: pointer;
            opacity: 0.8;
        }
    `,
    panelIcon: css`
        height: 25px;
        width: 25px;
    `,
    panelValue: css`
        color: white;
        font-size: 18px;
        font-weight: 400;
        margin-left: 18px;
    `
}