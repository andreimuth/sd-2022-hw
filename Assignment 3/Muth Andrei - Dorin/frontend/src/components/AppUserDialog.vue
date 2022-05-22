<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Create a review </v-toolbar>
        <v-form>
          <v-text-field v-model="review.text" label="Text" />
          <v-select v-model="review.rating" label="Rating" :items="ratings">
          </v-select>
        </v-form>
        <v-card-actions>
          <v-btn @click="createReview">Review</v-btn>
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
  name: "AppUserDialog",
  props: {
    review: {
      type: Object,
      default: () => ({
        rating: "AVERAGE",
      }),
    },
    app: Object,
    opened: Boolean,
  },
  data() {
    return {
      ratings: ["POOR", "AVERAGE", "GOOD", "EXCELLENT"],
    };
  },
  methods: {
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
          this.$emit("closeAppUserDialog");
        });
    },
    download() {
      api.apps.download(this.app.id);
    },
    displayReviews() {
      this.$emit("displayReviews", this.app);
    },
  },
};
</script>

<style scoped></style>
