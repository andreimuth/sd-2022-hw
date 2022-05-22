import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  generateCsvReport() {
    return HTTP.get(BASE_URL + "/book/generate-report/CSV", {
      headers: authHeader(),
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "report.csv");
      document.body.appendChild(link);
      link.click();
      return response.data;
    });
  },
  generatePdfReport() {
    return HTTP.get(BASE_URL + "/book/generate-report/PDF", {
      headers: authHeader(),
      responseType: "arraybuffer",
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "reports.pdf");
      document.body.appendChild(link);
      link.click();
      return response.data;
    });
  },
};
