import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allApps() {
    return HTTP.get(BASE_URL + "/app/find-all", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  importApps() {
    return HTTP.get(BASE_URL + "/app/import-apps", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(app) {
    return HTTP.post(BASE_URL + "/app/add-app", app, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(app) {
    return HTTP.put(BASE_URL + "/app/update-app/" + app.id, app, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/app/delete-app/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterAll(filterRequest) {
    return HTTP.get(BASE_URL + "/app/filtered", {
      headers: authHeader(),
      params: {
        name: filterRequest.name,
        type: filterRequest.type,
        description: filterRequest.description,
        maxPrice: filterRequest.maxPrice,
      },
    }).then((response) => {
      return response.data;
    });
  },
  download(id) {
    return HTTP.get(BASE_URL + "/app/download/" + id, {
      headers: authHeader(),
      responseType: "arraybuffer",
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "application.pdf");
      document.body.appendChild(link);
      link.click();
      return response.data;
    });
  },
};
