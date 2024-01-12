import { create } from 'apisauce'

const api = create({
    baseURL: "http://localhost:8080/",
    timeout: 10000,
    maxContentLength: 10000000,
    maxBodyLength: 10000000,
});

export const getUserById = (id) => api.get("/api/account/get_by_id/" + id);

export const getUserByEmail = (email) => api.get("/api/account/get_by_email/" + email);

export const getUserByPhone = (phone) => api.get("/api/account/get_by_phone/" + phone);

export const connectUser = (email, password) => api.post("/api/account/connect", {email, password});

export const createUser = (firstName, lastName, email, phoneNumber, password) => api.post("/api/account/create", {firstName, lastName, email, phoneNumber, password});

export const getAllCategories = (supermarketId) => api.get("/api/category/get_all/" + supermarketId);

export const getAllSubcategories = (categoryId) => api.get("/api/subcategory/get_all/" + categoryId);

export const getProductById = (productId) => api.get("/api/product/get_by_id/" + productId);

export const getAllProducts = (subcategoryId) => api.get("/api/product/get_all/" + subcategoryId);

export const getProductsBySupermarket = (supermarketId) => api.get("/api/product/get_all_by_supermarket/" + supermarketId);

export const decreaseStock = (productId) => api.patch("/api/product/decrease_stock/" + productId);

export const increaseStock = (productId) => api.patch("/api/product/increase_stock/" + productId);

export const searchProducts = (supermarketId, subcategoryId, input) => api.get("/api/product/search_product/" + supermarketId + "/" + subcategoryId + "?search=" + input);

export const createGroceryList = (userId, caddieId) => api.get("/api/grocery_list/create/" + userId + "/" + caddieId);

export const addProductToGroceryList = ({inListProduct}) => api.post("/api/in_list_product/add_to_list", inListProduct);

export const getAllInListProducts = (groceryListId) => api.get("/api/in_list_product/get_all/" + groceryListId);

export const deleteInListProduct = (inListProductId) => api.delete("/api/in_list_product/delete_from_list/" + inListProductId);

export const removeSimpleProduct = (inListProductId) => api.patch("/api/in_list_product/remove_simple_product/" + inListProductId);

export const pay = (groceryListId, totalPrice) => api.post("/api/grocery_list/pay/" + groceryListId, totalPrice);

export const getFinishedGroceryLists = (userId) => api.get("/api/grocery_list/get_finished/" + userId);

export const createTemplateList = (userId, props) => api.post("/api/template_list/create/" + userId, props);

export const getAllTemplates = (userId) => api.get("/api/template_list/get_all/" + userId);

export const deleteTemplate = (templateListId) => api.delete("/api/template_list/delete/" + templateListId);

export const getProductsIdByTemplate = (templateListId) => api.get("/api/template_list/get_products_id/" + templateListId);

export default api;