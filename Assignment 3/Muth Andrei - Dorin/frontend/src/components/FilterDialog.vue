<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Filter apps </v-toolbar>
        <v-form>
          <v-text-field v-model="filter.name" label="Name" />
          <v-select v-model="filter.appType" label="Type" :items="types"> </v-select>
          <v-text-field v-model="filter.description" label="Description" />
          <v-text-field v-model="filter.maxPrice" label="Price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="doFilter"> Filter </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "FilterDialog",
  props: {
    filter: {
      type: Object,
      default: () => ({}),
    },
    opened: Boolean,
  },
  data() {
    return {
      types: ["game", "dlc", "demo", "music", "video", "other"],
    };
  },
  methods: {
    doFilter() {
      api.apps
        .filterAll({
          name: this.filter.name,
          type: this.filter.appType,
          description: this.filter.description,
          maxPrice: parseFloat(this.filter.maxPrice),
        })
        .then((response) => {
          this.$emit("filterCompleted", response);
        });
    },
  },
};
</script>

<style scoped></style>
