import React, { Component } from 'react';
import Order from "./Order"

class Orders extends Component {

  render() {
    return (
        <div>
          <Order order={this.props.orders[0]} />
          <Order order={this.props.orders[1]} />
          <Order order={this.props.orders[2]} />
        </div>
      )
  }
}

export default Orders;