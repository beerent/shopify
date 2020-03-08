import React, { Component } from 'react';
import Orders from "./Orders"


class App extends Component {
  state = {
    orders : []
  };

  GetOrders = () => {
    fetch('http://localhost:8080/get')
    .then(res => res.json())
    .then((data) => {
      this.setState({ orders : data.orders })
    })
    .catch(console.log)
  }

  FetchOrders = () => {
    fetch('http://localhost:8080/fetch', {
      method: 'POST',
      headers: {
        'Accept': '*/*',
      },
      body: JSON.stringify({})
    }).then(() => {
      this.GetOrders();
    });
  }

  DeleteOrders = () => {
    fetch('http://localhost:8080/delete', {
      method: 'DELETE',
      headers: {
        'Accept': '*/*',
      },
      body: JSON.stringify({})
    }).then(() => {
      this.GetOrders();
    });
  }

  componentDidMount() {
    this.GetOrders()
  }

  render() {
    return (
      <Orders orders={this.state.orders} FetchOrders={this.FetchOrders} DeleteOrders={this.DeleteOrders} />
    );
  }
}

export default App;