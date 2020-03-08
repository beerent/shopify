import React, { Component } from 'react';

class Order extends Component {
  render() {
    if (!this.props.order) {
      return <div></div>
    }

    return (
      <div className="card">
        <div className="card-body">

          <p className="card-text">Stay Hungry, Stay Foolish</p>
        </div>
      </div>
    );
  }
}

export default Order;