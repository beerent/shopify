import React, { Component } from 'react';
import Order from "./Order"

class Orders extends Component {
  isComponentMounted = false;

  state = {
    orders : []
  };

  GetOrders = () => {
    this.DisableButtons();

    fetch('http://localhost:8080/get')
    .then(res => res.json())
    .then((data) => {
      if (this.isComponentMounted) {
        this.setState({ orders : data.orders })
      }
      this.EnableButtons();
    })
    .catch(console.log)

  }

  FetchOrders = () => {
    this.DisableButtons();

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
    this.DisableButtons();

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
        <div className="col text-center">
          <div className="btn-group">
            <button id="fetch_button" className="btn btn-default" className="btn btn-success" onClick={this.FetchOrders}>Fetch</button>
            <button id="delete_button" className="btn btn-default" className="btn btn-danger" onClick={this.DeleteOrders}>Delete All</button>
          </div> 
        </div>

        <div>{orderElements}</div>
        </div>
      )
  }
}

export default Orders;