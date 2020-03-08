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
      this.setState({ orders : data.orders })
    })
    .catch(console.log)
  }

  render() {
    return (
      <Orders orders={this.state.orders} />
    );
  }
}

export default App;