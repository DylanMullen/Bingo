import React from 'react';
import './App.css';

import Navbar from './res/js/components/navbar';
import Homepage from './res/js/pages/home';
import UserPage from './res/js/pages/Users';

function App() {
  return (
    <div className="app">
      <Navbar />
      <UserPage />
    </div>
  );
}

export default App;
