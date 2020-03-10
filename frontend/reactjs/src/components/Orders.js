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

    self.FetchStarted();

    fetch('http://localhost:8080/v1/orders')
    .then(res => res.json())
    .then((data) => {
      if (self.isComponentMounted) {
        self.setState({ orders : data.orders })
      }
      self.FetchFinished();
    })
    .catch(function(){
      self.FetchFinished();
    })

  }

  FetchOrders = () => {
    var self = this;

    self.FetchStarted();

    fetch('http://localhost:8080/v1/orders/fetch', {
      method: 'POST',
      body: JSON.stringify({})
    }).then(() => {
      self.GetOrders();
    })
    .catch(function(){
      self.FetchFinished();
    });
  }

  DeleteOrders = () => {
    var self = this;

    self.FetchStarted();

    fetch('http://localhost:8080/v1/orders', {
      method: 'DELETE',
      body: JSON.stringify({})
    }).then(() => {
      self.GetOrders();
    }).catch(function(){
      self.FetchFinished();
    })
  }

  componentDidMount() {
    this.isComponentMounted = true;
    this.GetOrders()
  }

  componentWillUnmound() {
    this.isComponentMounted = false;
  }

  FetchFinished() {
    this.EnableButtons();
    this.HideLoadingSymbol();
  }

  FetchStarted() {
    this.DisableButtons();
    this.ShowLoadingSymbol();
  }

  EnableButtons() {
    document.getElementById("fetch_button").disabled = false;
    document.getElementById("delete_button").disabled = false;
  }

  DisableButtons() {
    document.getElementById("fetch_button").disabled = true;
    document.getElementById("delete_button").disabled = true;
  }

  ShowLoadingSymbol() {
    document.getElementById("loading_symbol").style.display="block";
  }

  HideLoadingSymbol() {
    document.getElementById("loading_symbol").style.display="none";
  }

  render() {
    const orderElements = []

      this.state.orders.forEach(function(order, index){
        orderElements.push(<Order order={order} key={index} />)
      });
    return (
      <div>
        <div className="jumbotron text-center mb-0">
          <h1>Brent's Telescopes Sales</h1>
        </div>
        
        <div className="text-center mt-4 mb-4">
          <button id="fetch_button" className="btn btn-success button-width mr-4" onClick={this.FetchOrders}>Fetch</button>
          <button id="delete_button" className="btn btn-danger button-width ml-4" onClick={this.DeleteOrders}>Delete All</button>
        </div> 

        <div id="loading_symbol">
          <div className="d-flex justify-content-center">
            <div className="spinner-grow text-success" style={{width: '3rem', height: '3rem'}} role="status">
              <span className="sr-only">Loading...</span>
            </div>
          </div>
        </div>

        <div className="row justify-content-md-center">{orderElements}</div>
        </div>
      )
  }
}

export default Orders;