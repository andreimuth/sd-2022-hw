import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  generateCsvReport() {
    return HTTP.get(BASE_URL + "/book/generate-report/CSV", {
      headers: authHeader(),
    });
  },
  generatePdfReport() {
    return HTTP.get(BASE_URL + "/book/generate-report/PDF", {
      headers: authHeader(),
    });
  },
};
