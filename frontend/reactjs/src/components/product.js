import React, { Component } from 'react';

class Product extends Component {
  render() {
    return (
      <div className="card">
      <div className="card-header">Order #{this.props.order.id}</div>
        <div className="card-body">
          <h5 className="card-title">{this.props.order.user.first_name + " " + this.props.order.user.last_name}</h5>
          <h6 className="card-subtitle mb-2 text-muted">{this.props.order.user.email}</h6>
        </div>
      </div>
    );
  }
}

export default Product;