import { Text, TouchableOpacity, StyleSheet } from "react-native";
import { connectUser } from "../../api";
import { disconnectAccount } from "../../reducers/store";
import { useSelector } from "react-redux";

export default function Disconnection ({navigation, dispatch }) {
  const user = useSelector((state) => state.account.user);

  return (
    <TouchableOpacity onPress={() => {
      dispatch(disconnectAccount());
    }}>
      <Text style={styles.disconnection}>Déconnexion</Text>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  disconnection: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    alignSelf: "center",
    fontFamily: "IBMPlexSans-Bold",
    fontSize: 14,
    color: "#868585",
    textDecorationLine: "underline",
  }
});