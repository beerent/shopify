import React, { Component } from 'react';
import Order from "./Order"

class Orders extends Component {

  render() {
    const orderElements = []

    this.props.orders.forEach(function(order, index){
      orderElements.push(<Order order={order} key={index} />)
    })

    return (
      <div>
        <div className="col text-center">
          <div className="btn-group">
            <button className="btn btn-default" className="btn btn-success" onClick={this.props.FetchOrders}>Fetch</button>
            <button className="btn btn-default" className="btn btn-danger" onClick={this.props.DeleteOrders}>Delete All</button>
          </div> 
        </div>

        <div>{orderElements}</div>
        </div>
      )
  }
}

export default Orders;