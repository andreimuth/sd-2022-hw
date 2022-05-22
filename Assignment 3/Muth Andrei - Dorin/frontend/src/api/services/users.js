import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/user", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/auth/sign-up", user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(user) {
    return HTTP.put(BASE_URL + "/user/update-user/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/user/delete-user/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
