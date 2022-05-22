<template>
  <v-card>
    <v-card-title>
      Welcome, {{ getUsername }}!
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="importApps">Import apps</v-btn>
    <v-btn @click="addApp">Add app</v-btn>
    <v-btn @click="filterApps">Filter apps</v-btn>
    <v-btn @click="removeFilters">Remove filters</v-btn>
    <v-data-table
      :headers="appHeaders"
      :items="apps"
      @click:row="editApp"
    ></v-data-table>
    <v-card-title>
      Reviews for app
      <v-spacer></v-spacer>
    </v-card-title>
    <v-data-table :headers="reviewHeaders" :items="reviews"></v-data-table>
    <v-card-title>
      Forum
      <v-spacer></v-spacer>
    </v-card-title>
    <v-btn @click="createThread">Create new thread</v-btn>
    <v-data-table
      :headers="threadHeaders"
      :items="threads"
      @click:row="enterThread"
    ></v-data-table>
    <AppDialog
      :opened="appDialogVisible"
      @reviewCreated="reviewCreated"
      :app="selectedApp"
      @refresh="refreshAppList"
      @displayReviews="displayReviews"
    ></AppDialog>
    <FilterDialog
      :opened="filterDialogVisible"
      @filterCompleted="filterCompleted"
    ></FilterDialog>
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
import AppDialog from "@/components/AppDialog";
import FilterDialog from "@/components/FilterDialog";
import ThreadDialog from "@/components/ThreadDialog";
import ThreadCommentDialog from "@/components/ThreadCommentDialog";

export default {
  name: "AdminView",
  components: { ThreadCommentDialog, ThreadDialog, AppDialog, FilterDialog },
  data() {
    return {
      threadHeaders: [{ text: "Threads", value: "title" }],
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
      appDialogVisible: false,
      filterDialogVisible: false,
      threadDialogVisible: false,
      threadCommentDialogVisible: false,
      selectedThread: {},
      selectedApp: {},
      threadComments: [],
    };
  },
  methods: {
    async importApps() {
      api.apps.importApps().then(
        async () => (this.apps = await api.apps.allApps()),
        (error) => alert(error.response.data.message)
      );
    },
    addApp() {
      this.appDialogVisible = true;
    },
    editApp(app) {
      this.selectedApp = app;
      this.appDialogVisible = true;
    },
    reviewCreated() {
      this.appDialogVisible = false;
    },
    async refreshAppList() {
      this.appDialogVisible = false;
      this.selectedApp = {};
      this.apps = await api.apps.allApps();
    },
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
    async refreshThreadList() {
      this.threadDialogVisible = false;
      this.threads = await api.threads.allThreads();
    },
    createThread() {
      this.threadDialogVisible = true;
    },
    async displayReviews(app) {
      this.reviews = await api.reviews.getReviewsForApp(app.id);
      this.appDialogVisible = false;
    },
    async enterThread(thread) {
      console.log(thread.id);
      this.threadComments = await api.threads.getComments(thread.id);
      this.$root.$emit("initializeComments", this.threadComments);
      this.selectedThread = thread;
      this.threadCommentDialogVisible = true;
    },
    closeThreadCommentDialog() {
      this.threadCommentDialogVisible = false;
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
