<template>
  <div>
    <v-sheet tile height="54" class="d-flex">
      <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
          <v-sheet height="720" width = "100%" >
      <v-calendar
        ref="calendar"
        v-model="value"
        :weekdays="weekday"
        :type="type"
        :events="events"
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
      ></v-calendar>
    </v-sheet>
      <v-spacer></v-spacer>
      <v-btn icon class="ma-2" @click="$refs.calendar.next()">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-sheet>
  </div>
</template>

<script>
import { AXIOS } from "../http-common.js";

export default {
  data: () => ({
    assignments: [],
    type: "month",
    mode: "stack",
    weekday: [6, 0, 1, 2, 3, 4, 5],
    value: "",
    events: [],
    colors: [
      "blue",
      "indigo",
      "deep-purple",
      "cyan",
      "green",
      "orange",
      "grey darken-1",
    ],
  }),
  async mounted() {
    await this.initialize();
    this.getEvents();
  },
  methods: {
    async initialize() {
      await AXIOS.get("assignments", {}).then((res) => {
        this.assignments = res.data;
          });
    },
    getEvents() {
      const events = [];
      const assignments = this.assignments
      const eventCount = this.assignments.length;
      for (let i = 0; i < eventCount; i++) {
        const dateTS = assignments[i].dueDate;
        const date = new Date(dateTS);
        events.push({
          name: assignments[i].title,
          start: date,
          color: this.colors[this.rnd(0, this.colors.length - 1)],
          timed: true,
        });
      }

      this.events = events;
    },
    getEventColor(event) {  
      return event.color;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
};
</script>
