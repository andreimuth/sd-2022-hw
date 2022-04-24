<template>
  <v-card>
    <v-card-title>
      Items
      <v-spacer></v-spacer>
      <v-text-field v-model="search.keyword"></v-text-field>
      <v-btn @click="filter">Filter</v-btn>
      <v-btn @click="deleteFilter">Delete filter</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      @click:row="sell"
    ></v-data-table>
    <SaleDialog
      :opened="saleDialogVisible"
      :sale="selectedBook"
      @refresh="refreshBooks"
    ></SaleDialog>
  </v-card>
</template>

<script>
import api from "../api";
import SaleDialog from "@/components/SaleDialog";

export default {
  name: "UserView",
  components: {
    SaleDialog,
  },
  props: {
    search: {
      type: Object,
      default: () => ({
        keyword: "",
      }),
    },
  },
  data() {
    return {
      books: [],
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      saleDialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    filter() {
      if (this.search.keyword.trim() !== "") {
        api.books.filter(this.search.keyword).then((response) => {
          console.log(response);
          this.books = response;
        });
      }
    },
    deleteFilter() {
      api.books.allBooks().then((response) => {
        this.books = response;
      });
    },
    refreshBooks() {
      this.saleDialogVisible = false;
      this.selectedBook = {};
      api.books.allBooks().then((response) => {
        this.books = response;
      });
    },
    sell(book) {
      this.selectedBook = book;
      this.saleDialogVisible = true;
      console.log(this.saleDialogVisible);
    },
  },
  async created() {
    this.books = await api.books.allBooks();
  },
};
</script>

<style scoped></style>
