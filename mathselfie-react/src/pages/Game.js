import { css } from '@emotion/css'
import React from 'react'
import Header from '../common/Header'
import happyIcon from '../assets/emoji-happy.png'
import sadIcon from '../assets/emoji-sad.png'
import angryIcon from '../assets/emoji-angry.png'
import surprisedIcon from '../assets/emoji-surprised.png'
import Countdown from 'react-countdown'
import { useGameCurrentProblem } from '../store/selectors/gameSelector'

const Game = () => {
    const currentProblem = useGameCurrentProblem()
  return (
    <div className={gameStyles.body}>
        <Header/>
        <TextBoard>
            <p>{`${currentProblem.firstNumber} ${currentProblem.operation} ${currentProblem.secondNumber} = ?`}</p>
        </TextBoard>
        <TextBoard>
            <CountdownTimer/>
        </TextBoard>
        <div style={{flex: 1}} />
        <PanelBoard
            happy={currentProblem.options.HAPPY}
            sad={currentProblem.options.SAD}
            angry={currentProblem.options.ANGRY}
            surprised={currentProblem.options.SURPRISED}
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

const CountdownTimer = () => {
    return (
        <Countdown date={Date.now() + 5000} />
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
        width: 100vw;
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