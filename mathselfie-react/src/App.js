import React from 'react'
import Game from './pages/Game';
import Home from './pages/Home';
import { useAppPageSelector } from './store/selectors/appSelectors';
import routes from './constants/routes'
import Score from './pages/Score';

function App() {
  const currentPage = useAppPageSelector();
  return (
    <div className="App">
      <PageRouter currentPage={currentPage} />
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