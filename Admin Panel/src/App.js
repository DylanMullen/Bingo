import React from 'react';
import './App.css';

import Navbar from './res/js/components/navbar';
import Homepage from './res/js/pages/home';

function App() {
  return (
    <div className="app">
      <Navbar />
      <Homepage />
    </div>
  );
}

export default App;
