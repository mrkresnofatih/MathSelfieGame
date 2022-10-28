import React, { useEffect } from 'react'
import { useGameAboutToEnd, useGameProblemSetId } from '../store/selectors/gameSelector'
import getGameAPI from '../api/getGameAPI';
import Pusher from 'pusher-js';
import { useDispatch } from 'react-redux';
import { goToHomePage } from '../store/slices/appSlice';

const PusherListener = ({children}) => {
    const problemSetId = useGameProblemSetId();
    const gameAboutToEnd = useGameAboutToEnd();
    const dispatch = useDispatch()

    useEffect(() => {

        const pusher = new Pusher("362e47c543841cd0393c", {
            cluster: "ap1",
        });

        const channel = pusher.subscribe(problemSetId);
        channel.bind("submit-answer-success", () => {
            getGameAPI(problemSetId, gameAboutToEnd ? () => {
                dispatch(goToHomePage())            
            }: () => {})
        })
    // eslint-disable-next-line
    }, [problemSetId])
  return (
    <>{children}</>
  )
}

export default PusherListener