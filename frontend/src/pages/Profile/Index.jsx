import React from "react";
import { useDispatch, useSelector } from "react-redux";
import Header from "../../components/ProfileComponents/Header";
import ProfileInfos from "../../components/ProfileComponents/ProfileInfos";
import { Image, ScrollView, View, StyleSheet, Dimensions } from "react-native";
import Promotions from "../../components/ProfileComponents/Promotions";
import Disconnection from "../../components/ProfileComponents/Disconnection";

export default function Index({ navigation }) {
  const user = useSelector((state) => state.account.user);
  const caddie = useSelector((state) => state.caddie.usedCaddie);
  const dispatch = useDispatch();

  return (
      <ScrollView>
        <Header user={user} caddie={caddie} navigation={navigation}/>
          <View style={{gap: 20, marginTop: 170}}>
            {
              caddie
                ?
                <Promotions navigation={navigation} width={"90%"}/>
                :
                null
            }
            <ProfileInfos user={user}/>
            <Disconnection navigation={navigation} dispatch={dispatch}/>
          </View>
        <View style={{height: 10}}></View>
      </ScrollView>
    )
}
