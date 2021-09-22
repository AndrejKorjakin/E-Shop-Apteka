import axios from '../custom-axios/axios';

const EShopService = {
    fetchManufacturers: () => {
        return axios.get("/manufacturers");
    },
    fetchCategories: () => {
        return axios.get("/categories");
    },
    fetchProducts: () => {
        return axios.get("/lekovi");
    },
    deleteProduct: (id) => {
        return axios.delete(`/lekovi/delete/${id}`);
    },
    addProduct: (name, price, quantity, category, manufacturer) => {
        return axios.post("/lekovi/add", {
            "name" : name,
            "price" : price,
            "quantity" : quantity,
            "category" : category,
            "manufacturer" : manufacturer
        });
    },
    editProduct: (id, name, price, quantity, category, manufacturer) => {
        return axios.put(`/lekovi/edit/${id}`, {
            "name" : name,
            "price" : price,
            "quantity" : quantity,
            "category" : category,
            "manufacturer" : manufacturer
        });
    },
    getProduct: (id) => {
        return axios.get(`/lekovi/${id}`);
    }
}

export default EShopService;
