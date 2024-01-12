import { ActivityIndicator, Image, TouchableOpacity, View, StyleSheet, FlatList, Text } from "react-native";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setCategory } from "../../reducers/store";
import { getAllProducts } from "../../api";
import Product from "../../components/GroceriesComponents/Product";
import SearchBar from "../../components/GroceriesComponents/SearchBar";
import Products from "../../components/GroceriesComponents/Product";
import SearchProducts from "../../components/GroceriesComponents/SearchProducts";
import Loading from "../../components/Loading";

export default function ProductsPage({ navigation }) {
  const dispatch = useDispatch();
  const category = useSelector((state) => state.products.category);

  const [loading, setLoading] = React.useState(true);
  const [refreshing, setRefreshing] = React.useState(false);
  const [products, setProducts] = React.useState([]);
  const [showProducts, setShowProducts] = React.useState(true);
  const [searchText, changeSearchText] = React.useState('');


  React.useEffect(() => {
    navigation.setOptions({
      headerLeft: () => (
        <View style={{ marginLeft: 10 }}>
          <TouchableOpacity onPress={() => {
            navigation.goBack();
            dispatch(setCategory(null));
          }} style={{ padding: 10 }}>
            <Image
              source={require("../../images/left-arrow.png")}
              style={{ width: 16, height: 16 }}
              resizeMode="contain"
            />
          </TouchableOpacity>
        </View>
      ),
    });

    async function getProducts () {
      const response = await getAllProducts(category.value);
      if (response.status === 200) {
        setProducts(response.data);
      }

      setLoading(false);
    }
    getProducts();

  }, [])

  if (loading) {
    return (
      <Loading />
    )
  }

  const refreshProducts = async () => {
    const startingTime = Date.now();
    setRefreshing(true);

    const response = await getAllProducts(category.value);
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

  // Not clean at all, but the shadow is being cropped by the FlatList
  return (
    <>
      <View style={{ paddingHorizontal: 20, marginTop: 20 }}>
        <SearchBar show={setShowProducts} searchText={searchText} changeSearchText={changeSearchText}/>
        <View style={{borderBottomColor: "rgb(180,180,180)", borderBottomWidth: 1, width: "100%", marginVertical: 10, alignSelf: "center"}}></View>
      </View>
      {
        showProducts
          ?
          <Products products={products} refreshing={refreshing} refreshProducts={refreshProducts} />
          :
          <SearchProducts searchText={searchText} />
      }
    </>
  )
}
