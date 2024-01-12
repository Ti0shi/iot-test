import { useDispatch, useSelector } from "react-redux";
import { getAllInListProducts } from "../../api";
import {
  ActivityIndicator,
  StyleSheet,
  View
} from "react-native";
import EmptyProductList from "../../components/GroceryList/EmptyProductList";
import React from "react";
import { hasToBeUpdate, updateGroceryList } from "../../reducers/store";
import InListProducts from "../../components/GroceryList/InListProducts";
import Recap from "../../components/GroceryList/Recap";

export default function InListProductsPage () {
  const groceryList = useSelector((state) => state.associatedGroceryList.groceryList);
  const hasToBeUpdated = useSelector((state) => state.update.needUpdate);

  const [loading, setLoading] = React.useState(true);
  const [refreshing, setRefreshing] = React.useState(false);

  const [onlyManualAdd, setOnlyManualAdd] = React.useState(false);
  const [onlyGroceryAdd, setOnlyGroceryAdd] = React.useState(false);

  const dispatch = useDispatch();

  React.useEffect(() => {
    async function getInListProducts() {
      const response = await getAllInListProducts(groceryList.id);
      if (response.status === 200) {
        dispatch(updateGroceryList(response.data));
      }
      dispatch(hasToBeUpdate(false));
      setLoading(false);
    }
    getInListProducts();
  }, [hasToBeUpdated]);


  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#FFFFFF" />
      </View>
    );
  }

  const refreshProducts = async () => {
    const startingTime = Date.now();
    setRefreshing(true);

    const response = await getAllInListProducts(groceryList.id);
    if (response.status === 200) {
      dispatch(updateGroceryList(response.data));
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


  let products = groceryList.groceries;
  if (onlyManualAdd) {
    products = products.filter((product) => product.quantity > 0);
  }
  else if (onlyGroceryAdd) {
    products = products.filter((product) => product.quantity !== product.wantedQuantity);
  }
  else {
    products = groceryList.groceries;
  }

  return (
    <>
      {
        groceryList.groceries.length !== 0
          ?
          <>
            <Recap onlyManualAdd={onlyManualAdd} setOnlyManualAdd={setOnlyManualAdd} onlyGroceryAdd={onlyGroceryAdd} setOnlyGroceryAdd={setOnlyGroceryAdd}/>
            <View style={{borderBottomColor: "rgb(180,180,180)", borderBottomWidth: 1, width: "90%", marginVertical: 20, alignSelf: "center"}}></View>
            <InListProducts products={products} refreshing={refreshing} refreshProducts={refreshProducts}/>
          </>
          :
          <EmptyProductList/>
      }
    </>
  )
}

const styles = StyleSheet.create({
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});