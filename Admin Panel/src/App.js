import React from 'react';
import './App.css';

import Navbar from './res/js/components/navbar';
import Homepage from './res/js/pages/home';
import UserPage from './res/js/pages/Users';
import LoginPage from './res/js/pages/Login';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import GameListPage from './res/js/pages/GameList';



function App() {
  return (
    <Router>
      <div className="app">
        <Switch>
          <Route path="/login">
            <LoginPage />
          </Route>
          <Route path="/home">
            <Navbar />
            <Homepage />
          </Route>
          <Route path="/users">
            <Navbar />
            <UserPage />
          </Route>
          <Route path="/games">
            <Navbar />
            <GameListPage />
          </Route>
        </Switch>
      </div>
    </Router>

  );
}

export default App;

