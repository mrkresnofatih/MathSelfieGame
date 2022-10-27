import { css } from '@emotion/css';
import React from 'react'
import Header from '../common/Header';
import { useGameScore } from '../store/selectors/gameSelector'

const Score = () => {
    const gameScore = useGameScore();
    return (
        <div className={scoreStyles.body}>
            <Header/>
            <div className={scoreStyles.central}>
                Score: {gameScore}
            </div>
        </div>
    )
}

export default Score

const scoreStyles = {
    body: css`
        background-color: #06283D;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        color: white;
        font-size: 24px;
        font-weight: 600;
    `,
    central: css`
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
    `
}