import React from "react";
import { useDispatch, useSelector } from "react-redux";
import NoCaddie from "../../components/NoCaddie";
import InListProductsPage from "./InListProductsPage";

import Simulation from "../../components/GroceryList/Simulation";
import { View } from "react-native";

export default function Index({ navigation }) {
    const caddie = useSelector((state) => state.caddie.usedCaddie);
    const simulation = false;

    return (
      <>
          {
              caddie
                ?
                <View style={{flex: 1, marginBottom: simulation ? 480 : 340}}>
                    {
                        simulation
                          ?
                          <Simulation/>
                          :
                          null
                    }
                    <InListProductsPage/>
                </View>
                :
                <NoCaddie/>
          }
      </>

    )
}
