import { Image, Text, TouchableOpacity, View, StyleSheet } from "react-native";
import CheckBox from "expo-checkbox";
import React from "react";

export default function Filters ({setSortAlphabetically, setSortPrice, sortAlphabetically, sortPrice}) {
  const [showFilter, setShowFilter] = React.useState(false);

  const styles = StyleSheet.create({
    filterButton: {
      marginTop: 5,
      marginHorizontal: 20,
      marginBottom : !showFilter ? 10 : 0,
      flexDirection: "row",
      gap: 4,
    }
  })

  return (
    <>
      <TouchableOpacity style={styles.filterButton} onPress={() => {
        setShowFilter(!showFilter);
      }
      }>
        <Image source={require("../../images/list/filter.png")} style={{width: 15, height: 15, alignSelf: "center"}}/>
        <Text style={{fontFamily: "IBMPlexSans-Medium", fontSize: 20}}>Filtres</Text>
      </TouchableOpacity>

      {
        showFilter
          ?
          <View style={{flexDirection: "row", marginHorizontal: 30}}>
            <View style={{borderLeftColor: "#868686", borderLeftWidth: 2, height: "80%", alignSelf: "center", marginHorizontal: 10}}></View>
            <View style={{gap: 5, marginBottom: 10}}>
              <Text style={{fontFamily: "IBMPlexSans-Medium", fontSize: 18}}>Trier par :</Text>
              <View style={{flexDirection: "row", alignItems: "center", gap: 5}}>
                <CheckBox
                  value={sortAlphabetically}
                  onValueChange={setSortAlphabetically}
                  color={sortAlphabetically ? '#002495' : "#000"}
                />
                <Text style={{fontFamily: "IBMPlexSans-Light", fontSize: 14}}> Label (A-Z) </Text>
              </View>
              <View style={{flexDirection: "row", alignItems: "center", gap: 5}}>
                <CheckBox
                  value={sortPrice}
                  onValueChange={setSortPrice}
                  color={sortPrice ? '#002495' : "#000"}
                />
                <Text style={{fontFamily: "IBMPlexSans-Light", fontSize: 14}}> Prix croissant </Text>
              </View>
            </View>
          </View>
          :
          null
      }
    </>
  );
}