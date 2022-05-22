<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-btn @click="refreshReviews">Refresh</v-btn>
      <v-data-table :headers="reviewHeaders" :items="reviews"></v-data-table>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ReviewDialog",
  app: Object,
  props: {
    opened: Boolean,
  },
  data() {
    return {
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
    };
  },
  methods: {
    refreshReviews() {
      console.log(this.app);
      this.reviews = api.reviews.getReviewsForApp(this.app.id);
    },
  },
  displayReviewsInDialog(reviews) {
    this.reviews = reviews;
  },
};
</script>

<style scoped></style>
