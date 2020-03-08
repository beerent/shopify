import React, { Component } from 'react';

class Order extends Component {
  render() {
    if (!this.props.order) {
      return <div></div>
    }

    return (
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">{this.props.order.user.first_name + " " + this.props.order.user.last_name}</h5>
          <h6 className="card-subtitle mb-2 text-muted">{this.props.order.user.email}</h6>
          
          <p className="card-text">Stay Hungry, Stay Foolish</p>
        </div>
      </div>
    );
  }
}

export default Order;