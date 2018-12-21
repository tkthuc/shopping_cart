import axios from 'axios';

export default {
    getProducts : function() {
        return axios.get("http://localhost:8080/api/products");
    }
}