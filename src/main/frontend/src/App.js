import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import ProductBox from './components/product-box';
import productService from './api/productService';

class App extends Component {


    constructor(props) {
        super(props);
        this.state = {
            products: []
        }
    }

    async componentDidMount() {
          let products = await productService.getProducts();
          this.setState({
              products: products.data
          });
    }

    render() {
        return (
          <div className="App">
              <div className="App-header">
                    <div className="company-name"> Spring Commerce </div>
                    <div className="page-section"> Home </div>
              </div>

              <div className="App-content">
                  {
                      this.state.products.map(
                          (product,index) => <ProductBox key={index} productName={product.name} price={product.price} quantity="1"/>
                     )
                  }
              </div>

          </div>
        );
    }
}

export default App;
