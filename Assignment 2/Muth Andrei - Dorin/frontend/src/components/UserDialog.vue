<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNewUser ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.name" label="Username" />
          <v-text-field
            type="password"
            v-model="user.password"
            label="Password"
          />
          <v-text-field v-model="user.email" label="Email" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNewUser ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteUser" v-if="!isNewUser">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNewUser) {
        api.users
          .create({
            username: this.user.name,
            password: this.user.password,
            email: this.user.email,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      } else {
        api.users
          .edit({
            id: this.user.id,
            username: this.user.name,
            password: this.user.password,
            email: this.user.email,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      }
    },
    deleteUser() {
      api.users.delete(this.user.id).then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNewUser: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
