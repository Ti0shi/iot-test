import {
  StyleSheet,
  TextInput,
} from "react-native";
import React, { useState } from "react";

export default function SearchBar({ show, searchText, changeSearchText }) {
  return (
    <>
      <TextInput
        style={[styles.textInputs, styles.textInputsContents, {fontFamily: searchText === '' ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-SemiBold"}]}
        placeholder="Rechercher un article"
        onChangeText={(text) => {
          changeSearchText(text);
          if (text === "" || text === undefined || text === null)
            show(true);
          else
            show(false);
        }}
        value={searchText}
        autoCapitalize='none'
        autoCorrect={true}
      />
    </>
  )
}

const styles = StyleSheet.create({
  textInputs: {
    height: 55,
    width: "100%",
    backgroundColor: "#fff",
    borderRadius: 5,
    borderWidth: 2,
    borderColor: "#002495",
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 20,
    shadowOpacity: 0.2,
  },
  textInputsContents: {
    paddingLeft: 10,
    color: "#002495",
  },
});