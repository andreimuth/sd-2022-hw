import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allThreads() {
    return HTTP.get(BASE_URL + "/thread/find-all", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(thread) {
    return HTTP.post(BASE_URL + "/thread/add-thread", thread, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getComments(id) {
    return HTTP.get(BASE_URL + "/thread/get-comments/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  postComment(comment) {
    return HTTP.post(BASE_URL + "/thread/post-comment", comment, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  subscribeToThread(subscription) {
    return HTTP.patch(BASE_URL + "/thread/subscribe", subscription, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  updateUsersComments(usersComments) {
    return HTTP.patch(
      BASE_URL + "/thread/update-users-comments",
      usersComments,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
};
