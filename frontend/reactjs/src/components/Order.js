import React, { Component } from 'react';
import {formatPrice, formatPhoneNumber} from "../util/util.js"

class Order extends Component {

  render() {
    const productElements = []
    this.props.order.products.forEach(function(product, index){
      productElements.push(
        <li className="list-group-item" key={index}>
          <div className="row container">
            <div className="col-md-5"><span className="float-left">(x{product.quantity}) {product.name}</span></div>
            <div className="col-md-4"><span className="float-right">${formatPrice(product.price)} x {product.quantity}</span></div>
            <div className="col-md-3"><span className="float-right">${formatPrice((product.price * product.quantity))}</span></div>
          </div>
        </li>)
    });

    const total = this.props.order.products.reduce((prevTotal, product) => {
        return prevTotal + (product.price * product.quantity);
    }, 0);

    productElements.push(
      <li className="list-group-item" key="totalId">
        <div className="row container">
          <div className="col-md-5"></div>
          <div className="col-md-4"><span className="float-right"><b>Total</b></span></div>
          <div className="col-md-3"><span className="float-right">${formatPrice(total)}</span></div>
        </div>
      </li>)

    return (
      <div className="card mb-3 mr-2 ml-2" style={{width : '50rem'}}>
        <div className="card-header">
          <div className="row container">
            <div className="col-md-6"><span className="float-left"><b>Order #{this.props.order.id}</b></span></div>
            <div className="col-md-6"><span className="float-right">{this.props.order.date_ordered}</span></div>
          </div>
        </div>
        <div className="card-body">
          <h5 className="card-title">{this.props.order.user.first_name + " " + this.props.order.user.last_name}</h5>
          <div><h6 className="card-subtitle mb-2">{this.props.order.user.email}</h6></div>
          <div><h6 className="card-subtitle mb-2">{formatPhoneNumber(this.props.order.user.phone_number)}</h6></div>
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