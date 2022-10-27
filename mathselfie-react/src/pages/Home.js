import { css } from '@emotion/css'
import React from 'react'
import { useDispatch } from 'react-redux'
import newGameAPI from '../api/newGameAPI'
import logo from '../assets/logo.png'
import { goToGamePage } from '../store/slices/appSlice'
import { resetGame } from '../store/slices/gameSlice'

const Home = () => {
    const dispatch = useDispatch();
    const playGame = () => {
        dispatch(resetGame())
        newGameAPI(() => {
            dispatch(goToGamePage())
        })
    }

  return (
    <div className={homeStyles.body}>
        <img src={logo} alt="" />
        <h1 className={homeStyles.title}>MathSelfieGame</h1>
        <h3 className={homeStyles.subtitle}>Arithmetics Quiz with Selfies</h3>
        <label className={homeStyles.button} onClick={playGame}>Play Game</label>
    </div>
  )
}

export default Home

const homeStyles = {
    body: css`
        background-color: #06283D;
        height: 100vh;
        width: 100vw;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    `,
    title: css`
        font-size: 36px;
        font-weight: 600;
        color: white;
        margin: 24px 0 0;
    `,
    subtitle: css`
        font-size: 24px;
        font-weight: 200;
        color: white;
        margin: 12px 0;
    `,
    button: css`
        width: 120px;
        background-color: #47B5FF;
        padding: 12px;
        margin-top: 200px;
        border-radius: 90px;
        font-size: 18px;
        text-align: center;

        :hover {
            cursor: pointer;
            opacity: 0.8;
        }
    `
}