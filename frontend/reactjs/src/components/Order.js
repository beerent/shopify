import React, { Component } from 'react';

class Order extends Component {
  render() {
    const productElements = []

    this.props.order.products.forEach(function(product, index){
      productElements.push(<li className="list-group-item" key={index}><div>{index+1}. {product.name}</div></li>)
    });

    return (
      <div className="card mb-3">
      <div className="card-header">Order #{this.props.order.id}</div>
        <div className="card-body" data-toggle="collapse">
          <h5 className="card-title">{this.props.order.user.first_name + " " + this.props.order.user.last_name}</h5>
          <div><h6 className="card-subtitle mb-2 text-muted">{this.props.order.user.email}</h6></div>
          <div><h6 className="card-subtitle mb-2 text-muted">{this.props.order.user.phone_number}</h6></div>
          <p className="card-text"></p>

          <ul className="list-group list-group-flush">
            <li className="list-group-item"></li>
            {productElements}
            <li className="list-group-item"></li>
          </ul>
        </div>
      </div>
    );
  }
}

export default Order;