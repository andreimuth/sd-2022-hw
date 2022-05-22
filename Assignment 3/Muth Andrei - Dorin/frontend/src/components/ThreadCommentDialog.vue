<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="800"
    :value="opened"
  >
    <template>
      <v-card>
        <v-card-title>
          {{ thread.title }}
          <v-spacer></v-spacer>
        </v-card-title>
        <v-btn @click="subscribeToThread">Subscribe to this thread</v-btn>
        <div class="about" v-if="opened">
          <ul>
            <li v-for="(indexComment, index) in comments" :key="index">
              <div>
                <p>{{ indexComment.username }}: {{ indexComment.text }}</p>
              </div>
            </li>
          </ul>
        </div>
        <v-text-field v-model="comment.text" label="Comment" />
        <v-btn @click="postComment">Post comment</v-btn>
        <v-btn @click="close">Close</v-btn>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
"use strict";
import api from "@/api";

import * as Stomp from "webstomp-client";

import SockJS from "sockjs-client";

//let stompClient;

export default {
  name: "ThreadCommentDialog",
  props: {
    thread: { type: Object, default: () => {} },
    opened: Boolean,
  },
  data() {
    return {
      connection: null,
      comment: {
        type: Object,
        default: () => ({
          text: "",
        }),
      },
      comments: { type: Array, default: () => [] },
    };
  },
  methods: {
    async postComment() {
      if (
        this.comment.text === undefined ||
        this.comment.text === null ||
        this.comment.text === ""
      ) {
        alert("You cannot post empty comments");
        return;
      }
      let comment = {
        threadId: this.thread.id,
        text: this.comment.text,
        user: JSON.parse(localStorage.getItem("user")),
      };
      this.stompClient.send("/forum/post-comment", JSON.stringify(comment));
      this.comment.text = "";
    },
    close() {
      this.onDisconnected();
      this.$emit("close");
    },
    onDisconnected() {
      this.stompClient.unsubscribe("/topic/comments");
    },
    subscribeToThread() {
      api.threads
        .subscribeToThread({
          threadId: this.thread.id,
          user: JSON.parse(localStorage.getItem("user")),
        })
        .then((response) => {
          alert(response.message);
        });
    },
  },
  mounted() {
    const onMessageReceived = async (payload) => {
      let comment = JSON.parse(payload.body);
      if (this.opened && comment.threadId === this.thread.id) {
        console.log("You have received a message: " + comment.text);
        this.comments = await api.threads.getComments(this.thread.id);
        await api.threads.updateUsersComments({
          threadId: this.thread.id,
          user: JSON.parse(localStorage.getItem("user")),
        });
      }
    };

    const onConnected = () => {
      console.log("Connection to websocket established");
      this.stompClient.subscribe("/topic/comments", onMessageReceived);
      api.threads.updateUsersComments({
        threadId: this.thread.id,
        user: JSON.parse(localStorage.getItem("user")),
      });
    };

    function onError() {
      console.log("onError");
    }

    this.$root.$on("initializeComments", (comments) => {
      this.comments = comments;
      this.socket = new SockJS("http://localhost:8060/forum");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, onConnected, onError);
    });
  },
};
</script>

<style scoped></style>
