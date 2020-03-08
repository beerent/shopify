import React, { Component } from 'react';
import Orders from "./Orders"


class App extends Component {
  state = {
    orders : {}
  };

  componentDidMount() {
    fetch('http://localhost:8080/get')
    .then(res => res.json())
    .then((data) => {
      this.setState({ contacts: data })
    })
    .catch(console.log)
  }

  render() {
    return (
      <Orders />
    );
  }
}

export default App;