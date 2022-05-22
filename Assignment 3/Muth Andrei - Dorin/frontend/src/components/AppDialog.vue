<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNewApp ? "Create app" : "Edit app" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="app.name" label="Name" />
          <v-select v-model="type" label="Type" :items="types"> </v-select>
          <v-text-field v-model="app.description" label="Description" />
          <v-text-field v-model="app.price" label="Price" />
        </v-form>
        <v-form v-if="!isNewApp">
          <v-text-field v-model="review.text" label="Review text" />
          <v-select v-model="review.rating" label="Rating" :items="ratings">
          </v-select>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNewApp ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteApp" v-if="!isNewApp">Delete</v-btn>
          <v-btn @click="createReview" v-if="!isNewApp">Create review</v-btn>
          <v-btn @click="download">Download</v-btn>
          <v-btn @click="displayReviews">See reviews</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "AppDialog",
  props: {
    app: Object,
    review: {
      type: Object,
      default: () => ({
        rating: "AVERAGE",
      }),
    },
    opened: Boolean,
  },
  data() {
    return {
      type: "game",
      types: ["game", "dlc", "demo", "music", "video", "other"],
      ratings: ["POOR", "AVERAGE", "GOOD", "EXCELLENT"],
    };
  },
  methods: {
    persist() {
      if (this.isNewApp) {
        api.apps
          .create({
            name: this.app.name,
            type: this.type,
            description: this.app.description,
            price: this.app.price,
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      } else {
        api.apps
          .edit({
            id: this.app.id,
            name: this.app.name,
            type: this.type,
            description: this.app.description,
            price: parseFloat(this.app.price),
          })
          .then(
            () => this.$emit("refresh"),
            (error) => alert(error.response.data.message)
          );
      }
    },
    deleteApp() {
      api.apps.delete(this.app.id).then(() => this.$emit("refresh"));
    },
    createReview() {
      api.reviews
        .create({
          text: this.review.text,
          rating: this.review.rating,
          app: this.app,
          user: JSON.parse(localStorage.getItem("user")),
        })
        .then(() => {
          alert("Review created");
          this.$emit("reviewCreated");
        });
    },
    download() {
      api.apps.download(this.app.id);
    },
    displayReviews() {
      this.$emit("displayReviews", this.app);
    },
  },
  computed: {
    isNewApp: function () {
      return !this.app.id;
    },
  },
};
</script>

<style scoped></style>
