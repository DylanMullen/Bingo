import React from 'react';
import './App.css';

import Navbar from './res/js/components/navbar';
import Homepage from './res/js/pages/home';
import UserPage from './res/js/pages/Users';
import LoginPage from './res/js/pages/Login';

function App() {
  return (
    <div className="app">
      {/* <Navbar />
      <UserPage /> */}
      <LoginPage />
    </div>
  );
}

export default App;
