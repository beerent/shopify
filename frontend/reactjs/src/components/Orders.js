import React, { Component } from 'react';
import Order from "./Order"

class Orders extends Component {

  render() {
    const items = []

    this.props.orders.forEach(function(order, index){
      items.push(<Order order={order} key={index} />)
    })


    return (
        <div>{items}</div>
      )
  }
}

export default Orders;