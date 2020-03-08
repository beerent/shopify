import React, { Component } from 'react';
import Order from "./Order"
import "../css/button.css"

class Orders extends Component {
  isComponentMounted = false;

  state = {
    orders : []
  };

  GetOrders = () => {
    var self = this;

    self.DisableButtons();

    fetch('http://localhost:8080/get')
    .then(res => res.json())
    .then((data) => {
      if (self.isComponentMounted) {
        self.setState({ orders : data.orders })
      }
      self.EnableButtons();
    })
    .catch(function(){
      self.EnableButtons();
    })

  }

  FetchOrders = () => {
    var self = this;

    self.DisableButtons();

    fetch('http://localhost:8080/fetch', {
      method: 'POST',
      headers: {
        'Accept': '*/*',
      },
      body: JSON.stringify({})
    }).then(() => {
      self.GetOrders();
    })
    .catch(function(){
      self.EnableButtons();
    })
  }

  DeleteOrders = () => {
    var self = this;

    self.DisableButtons();

    fetch('http://localhost:8080/delete', {
      method: 'DELETE',
      headers: {
        'Accept': '*/*',
      },
      body: JSON.stringify({})
    }).then(() => {
      self.GetOrders();
    });
  }

  componentDidMount() {
    this.isComponentMounted = true;
    this.GetOrders()
  }

  componentWillUnmound() {
    this.isComponentMounted = false;
  }

  EnableButtons() {
    document.getElementById("fetch_button").disabled = false;
    document.getElementById("delete_button").disabled = false;
  }

  DisableButtons() {
    document.getElementById("fetch_button").disabled = true;
    document.getElementById("delete_button").disabled = true;
  }

  render() {
    const orderElements = []

    if (this.state.orders) {
      this.state.orders.forEach(function(order, index){
        orderElements.push(<Order order={order} key={index} />)
      });
  }
    return (
      <div>
        <div className="jumbotron text-center mb-0">
          <h1>E-Commerce Dashboard</h1>
        </div>
          <div className="text-center mt-4 mb-4">
            <button id="fetch_button" className="btn" className="btn btn-success button-width mr-4" onClick={this.FetchOrders}>Fetch</button>
            <button id="delete_button" className="btn" className="btn btn-danger button-width ml-4" onClick={this.DeleteOrders}>Delete All</button>
          </div> 

        <div>{orderElements}</div>
        </div>
      )
  }
}

export default Orders;