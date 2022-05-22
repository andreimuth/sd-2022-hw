import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  create(review) {
    return HTTP.post(BASE_URL + "/review/add-review", review, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getReviewsForApp(id) {
    return HTTP.get(BASE_URL + "/app/get-reviews/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
