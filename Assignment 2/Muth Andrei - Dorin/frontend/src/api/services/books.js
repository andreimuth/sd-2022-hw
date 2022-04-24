import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/book/find-all", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(book) {
    return HTTP.post(BASE_URL + "/book/add-book", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(book) {
    return HTTP.put(BASE_URL + "/book/update-book/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/book/delete-book/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filter(word) {
    console.log(word);
    return HTTP.get(BASE_URL + "/book/filter-books/" + word, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(sale) {
    return HTTP.patch(BASE_URL + "/book/sell-books/" + sale.id, sale, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
