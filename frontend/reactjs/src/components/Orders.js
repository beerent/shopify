import React, { Component } from 'react';
import Order from "./Order"

class Orders extends Component {

  render() {
    const items = []

    for (const [index, value] of [this.props.orders].entries()) {
      items.push(<Order order={value} key={index} />)
    }

    return (
        <div>{items}</div>
      )
  }
}

export default Orders;