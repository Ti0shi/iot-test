import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { addProductToGroceryList, decreaseStock, increaseStock, removeSimpleProduct } from "../../api";
import { hasToBeUpdate } from "../../reducers/store";
import { useDispatch, useSelector } from "react-redux";


export default function Simulation() {
  const groceryList = useSelector((state) => state.associatedGroceryList.groceryList);
  const dispatch = useDispatch();

  return (
    <View style={{ gap: 10, marginTop: 20 }}>
      <TouchableOpacity style={styles.button} onPress={async () => {
        const randomId = Math.floor(Math.random() * 6) + 1;
        const inListProduct = {
          id: "NULL", // will be generated by the backend
          groceryListId: groceryList.id,
          productId: randomId.toString(),
          quantity: 1,
          wantedQuantity: 1,
          context: "MANUAL_ADDITION",
        }
        // await decreaseStock(randomId.toString());

        const response = await addProductToGroceryList({ inListProduct })
        if (response.status === 200) {
          Alert.alert("Alerte", "Vous venez d'ajouter un produit dans votre caddie !");
          dispatch(hasToBeUpdate(true));
        }
      }}>
        <Text style={styles.buttonText}>Simuler l'ajout d'un produit</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={async () => {
        const products = groceryList.groceries.filter((product) => product.quantity > 0);
        const randomId = Math.floor(Math.random() * products.length);
        const response = await removeSimpleProduct(products[randomId].id);
        await increaseStock(products[randomId].productId);

        if (response.status === 200) {
          Alert.alert("Alerte", "Vous venez de retirer un produit de votre caddie !");
          dispatch(hasToBeUpdate(true));
        }

      }}>
        <Text style={styles.buttonText}>Simuler la suppression d'un produit</Text>
      </TouchableOpacity>
    </View>
  )
}

const styles = StyleSheet.create({
  button: {
    backgroundColor: "#002495",
    height: 55,
    borderRadius: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 10,
    shadowOpacity: 0.1,
    justifyContent: "center",
    alignItems: "center",
    marginHorizontal: 20,
  },
  buttonText: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 18,
    color: "#FFF",
  }
});
