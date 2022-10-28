import { css, cx } from '@emotion/css'
import React from 'react'
import { useDispatch } from 'react-redux'
import logo from '../assets/logo.png'
import { goToHomePage } from '../store/slices/appSlice'
import { resetGame } from '../store/slices/gameSlice'

const Header = () => {
    const dispatch = useDispatch();
    const goHome = () => {
        dispatch(goToHomePage())
        dispatch(resetGame())
    }
  return (
    <div className={headerStyles.body}>
        <label className={cx(headerStyles.innerbody, headerStyles.home)} onClick={goHome}>
            <img src={logo} alt="" className={headerStyles.logo} />
            <h1 className={headerStyles.header}>MathSelfieGame</h1>
        </label>
    </div>
  )
}

export default Header

const headerStyles = {
    body: css`
        display: flex;
        align-items: center;
        background-color: #256D85;
        color: white;
        width: 100%;
    `,
    innerbody: css`
        display: flex;
        align-items: center;
        margin: 18px;
    `,
    header: css`
        color: white;
        font-size: 24px;
        font-weight: 600;
        margin-left: 12px;
    `,
    logo: css`
        height: 25px;
        width: 25px;
    `,
    home: css`
        :hover {
            cursor: pointer;
            opacity: 0.8;
        }
    `
}