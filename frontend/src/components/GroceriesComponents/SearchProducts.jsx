import React from "react";
import { getAllProducts, searchProducts } from "../../api";
import { ActivityIndicator, View, StyleSheet } from "react-native";
import Products from "./Product";
import { useSelector } from "react-redux";
import Loading from "../Loading";

export default function SearchProducts({ searchText }) {
  const subCategories = useSelector((state) => state.products.category) || "0";
  const supermarket = useSelector((state) => state.currentSupermarket.supermarket) || "0";

  const [products, setProducts] = React.useState([]);
  const [loading, setLoading] = React.useState(true);
  const [refreshing, setRefreshing] = React.useState(false);

  React.useEffect(() => {
    async function getProducts() {
      const response = await searchProducts(supermarket === "0" ? "0" : supermarket.id,
        subCategories === "0" ? "0" : subCategories.value, searchText);
      if (response.status === 200) {
        setProducts(response.data);
      }

      setLoading(false);
    }
    getProducts();
  }, [searchText]);

  if (loading) {
    return (
      <Loading />
    )
  }

  const refreshProducts = async () => {
    const startingTime = Date.now();
    setRefreshing(true);

    const response = await searchProducts(supermarket.id, subCategories.value, searchText);
    if (response.status === 200) {
      setProducts(response.data);
    }

    const endingTime = Date.now();
    const timeElapsed = endingTime - startingTime;

    if (timeElapsed < 600) {
      setTimeout(() => {
        setRefreshing(false);
      }, 600 - timeElapsed);
      return;
    }
  };

  return (
    <Products products={products} refreshing={refreshing} refreshProducts={refreshProducts} />
  )
}
