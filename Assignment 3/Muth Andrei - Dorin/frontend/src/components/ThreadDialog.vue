<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create thread </v-toolbar>
        <v-form>
          <v-text-field v-model="thread.title" label="Title" />
          <v-text-field v-model="thread.initialComment" label="Initial comment" />
        </v-form>
        <v-card-actions>
          <v-btn @click="createThread">Create thread</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ThreadDialog",
  props: {
    thread: {
      type: Object,
      default: () => ({}),
    },
    opened: Boolean,
  },
  methods: {
    createThread() {
      api.threads
        .create({
          title: this.thread.title,
          initialComment: this.thread.initialComment,
          user: JSON.parse(localStorage.getItem("user")),
        })
        .then(() => {
          this.$emit("refresh");
        });
    },
  },
};
</script>

<style scoped></style>
