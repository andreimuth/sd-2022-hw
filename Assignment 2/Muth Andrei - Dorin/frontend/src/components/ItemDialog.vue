<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNewBook ? "Create book" : "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.price" label="Price" />
          <v-text-field v-model="book.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNewBook ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteBook" v-if="!isNewBook">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNewBook) {
        api.books
          .create({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            price: parseFloat(this.book.price),
            quantity: parseInt(this.book.quantity),
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      } else {
        api.books
          .edit({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            price: parseFloat(this.book.price),
            quantity: parseInt(this.book.quantity),
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      }
    },
    deleteBook() {
      api.books.delete(this.book.id).then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNewBook: function () {
      return !this.book.id;
    },
  },
};
</script>

<style scoped></style>
