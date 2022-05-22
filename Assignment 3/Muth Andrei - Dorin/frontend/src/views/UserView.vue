<template>
  <v-card>
    <v-card-title>
      Welcome, {{ getUsername }}!
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="filterApps">Filter apps</v-btn>
    <v-btn @click="removeFilters">Remove filters</v-btn>
    <v-data-table
      :headers="appHeaders"
      :items="apps"
      @click:row="review"
    ></v-data-table>
    <v-card-title>
      Reviews for app
      <v-spacer></v-spacer>
    </v-card-title>
    <v-data-table :headers="reviewHeaders" :items="reviews"></v-data-table>
    <v-card-title>
      Threads
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="createThread">Create new thread</v-btn>
    <v-data-table
      :headers="threadHeaders"
      :items="threads"
      @click:row="enterThread"
    ></v-data-table>
    <FilterDialog
      :opened="filterDialogVisible"
      @filterCompleted="filterCompleted"
    ></FilterDialog>
    <AppUserDialog
      :opened="appUserDialogVisible"
      :app="selectedApp"
      @displayReviews="displayReviews"
      @closeAppUserDialog="closeAppUserDialog"
    ></AppUserDialog>
    <ThreadDialog
      :opened="threadDialogVisible"
      @refresh="refreshThreadList"
    ></ThreadDialog>
    <ThreadCommentDialog
      :opened="threadCommentDialogVisible"
      :thread="selectedThread"
      @close="closeThreadCommentDialog"
    ></ThreadCommentDialog>
  </v-card>
</template>

<script>
import api from "../api";
import FilterDialog from "@/components/FilterDialog";
import AppUserDialog from "@/components/AppUserDialog";
import ThreadDialog from "@/components/ThreadDialog";
import ThreadCommentDialog from "@/components/ThreadCommentDialog";

export default {
  name: "UserView",
  components: {
    AppUserDialog,
    FilterDialog,
    ThreadDialog,
    ThreadCommentDialog,
  },
  data() {
    return {
      threadHeaders: [{ text: "Title", value: "title" }],
      threads: [],
      reviewHeaders: [
        {
          text: "Text",
          value: "text",
          sortable: false,
          align: "left",
        },
        {
          text: "Rating",
          value: "rating",
        },
      ],
      reviews: [],
      apps: [],
      appHeaders: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        {
          text: "Type",
          value: "type",
        },
        {
          text: "Description",
          value: "description",
        },
        {
          text: "Price",
          value: "price",
        },
      ],
      filterDialogVisible: false,
      appUserDialogVisible: false,
      threadDialogVisible: false,
      threadCommentDialogVisible: false,
      selectedThread: {},
      selectedApp: {},
      threadComments: [],
    };
  },
  methods: {
    filterApps() {
      this.filterDialogVisible = true;
    },
    filterCompleted(apps) {
      this.apps = apps;
      this.filterDialogVisible = false;
    },
    removeFilters() {
      api.apps.allApps().then((response) => {
        this.apps = response;
      });
    },
    review(app) {
      this.selectedApp = app;
      this.appUserDialogVisible = true;
    },
    async displayReviews(app) {
      this.reviews = await api.reviews.getReviewsForApp(app.id);
      this.appUserDialogVisible = false;
    },
    async refreshThreadList() {
      this.threadDialogVisible = false;
      this.threads = await api.threads.allThreads();
    },
    createThread() {
      this.threadDialogVisible = true;
    },
    async enterThread(thread) {
      this.threadComments = await api.threads.getComments(thread.id);
      this.$root.$emit("initializeComments", this.threadComments);
      this.selectedThread = thread;
      this.threadCommentDialogVisible = true;
    },
    closeThreadCommentDialog() {
      this.threadCommentDialogVisible = false;
    },
    closeAppUserDialog() {
      this.appUserDialogVisible = false;
    },
  },
  async created() {
    this.apps = await api.apps.allApps();
    this.threads = await api.threads.allThreads();
  },
  computed: {
    getUsername: function () {
      const data = JSON.parse(localStorage.getItem("user"));
      return data.username;
    },
  },
};
</script>

<style scoped></style>
