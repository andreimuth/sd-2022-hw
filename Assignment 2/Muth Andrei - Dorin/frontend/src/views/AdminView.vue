<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="addUser">Add user</v-btn>
    <v-data-table
      :headers="headers"
      :items="users"
      @click:row="editUser"
    ></v-data-table>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="addBook">Add book</v-btn>
    <v-data-table
      :headers="bookHeaders"
      :items="books"
      @click:row="editBook"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshBookList"
    ></ItemDialog>
    <UserDialog
      :opened="userDialogVisible"
      :user="selectedUser"
      @refresh="refreshUserList"
    ></UserDialog>
    <v-btn @click="generateCsv">Generate csv report</v-btn>
    <v-btn @click="generatePdf">Generate pdf report</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "@/components/ItemDialog";
import UserDialog from "@/components/UserDialog";
export default {
  name: "AdminView",
  components: { UserDialog, ItemDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      books: [],
      bookHeaders: [
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
      dialogVisible: false,
      selectedBook: {},
      userDialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },
    async refreshBookList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
    editUser(user) {
      this.selectedUser = user;
      this.userDialogVisible = true;
    },
    addUser() {
      this.userDialogVisible = true;
    },
    async refreshUserList() {
      this.userDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    generateCsv() {
      api.reports.generateCsvReport();
    },
    generatePdf() {
      api.reports.generatePdfReport();
    },
  },
  async created() {
    this.users = await api.users.allUsers();
    this.books = await api.books.allBooks();
  },
};
</script>

<style scoped></style>
