import React from 'react'
import Game from './pages/Game';
import Home from './pages/Home';
import { useAppPageSelector } from './store/selectors/appSelectors';
import routes from './constants/routes'
import Score from './pages/Score';
import PusherListener from './common/PusherListener';

function App() {
  const currentPage = useAppPageSelector();
  return (
    <div className="App">
      <PusherListener>
        <PageRouter currentPage={currentPage} />
      </PusherListener>
    </div>
  );
}

export default App;

const PageRouter = ({currentPage}) => {
  switch (currentPage) {
    case routes.Home:
      return <Home/>
    case routes.GAME:
      return <Game/>
    case routes.SCORE:
      return <Score/>
    default:
      return <Home/>
  }
}