<template>
  <div>
    <v-data-table
      class="row-pointer"
      :headers="headers"
      :items="courses"
      item-key="code"
      @click:row="editCourse"
    >
    </v-data-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      courses: [
        {
          code: "CS111",
          name: "Introduction to CS",
          members: [],
        },
        {
          code: "CS112",
          name: "Programming 1",
          members: [],
        },
        {
          code: "CS211",
          name: "Data Structures",
          members: [],
        },
        {
          code: "CS223",
          name: "Design Patterns",
          members: [],
        },
        {
          code: "CS311",
          name: "Algorithms",
          members: [],
        },
        {
          code: "CS322",
          name: "Networks",
          members: [],
        },
      ],
    };
  },
  computed: {
    headers() {
      return [
        {
          text: "Code",
          align: "start",
          value: "code",
        },
        {
          text: "Name",
          value: "name",
        },
      ];
    },
  },
  methods: {
    editCourse(row) {
      console.log(row);
    },
  },
};
</script>
<style lang="css" scoped>
.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
</style>
