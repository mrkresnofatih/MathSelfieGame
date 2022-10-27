import React from 'react'
import Game from './pages/Game';
import Home from './pages/Home';
import { useAppPageSelector } from './store/selectors/appSelectors';
import routes from './constants/routes'

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
    default:
      return <Home/>
  }
}