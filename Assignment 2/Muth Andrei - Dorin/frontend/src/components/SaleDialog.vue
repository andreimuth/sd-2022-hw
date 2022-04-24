<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Sell books </v-toolbar>
        <v-form>
          <v-text-field v-model="sale.quantity" label="quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="sell"> Sell </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SaleDialog",
  props: {
    sale: Object,
    opened: Boolean,
  },
  methods: {
    sell() {
      api.books
        .sell({
          id: this.sale.id,
          quantity: this.sale.quantity,
        })
        .then(
          () => {
            this.$emit("refresh");
          },
          (error) => {
            alert(error.response.data.message);
          }
        );
    },
  },
};
</script>

<style scoped></style>
