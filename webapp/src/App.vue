<script setup lang="ts">
import AccountForm from './components/AccountForm.vue'
import CrewMemberForm from './components/CrewMemberForm.vue'
import BookingForm from './components/BookingForm.vue'
import CabinsView from './components/CabinsView.vue'
import EmergencyShuttlesView from './components/EmergencyShuttlesView.vue'
</script>

<template>
  <h1 class="title">USpace</h1>
  <main class="grid-layout">
    <AccountForm class="account" />
    <BookingForm class="booking" />
    <CabinsView class="cabins" />
    <CrewMemberForm class="crew-member" />
    <EmergencyShuttlesView class="shuttles" />
  </main>
</template>

<style scoped>
.grid-layout {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(2, auto);
  grid-gap: 20px;
  padding: 20px;
}

.account {
  grid-column: 1 / 2;
  grid-row: 1 / 2;
}

.booking {
  grid-column: 2 / 3;
  grid-row: 1 / 2;
}

.crew-member {
  grid-column: 3 / 4;
  grid-row: 1 / 2;
}

.cabins {
  grid-column: 1 / 2;
  grid-row: 2 / 3;
}

.shuttles {
  grid-column: 2 / 4;
  grid-row: 2 / 3;
}

.title {
   font-weight: 500;
   font-size: 2.6rem;
   text-align: center;
   margin-bottom: 20px;
 }
</style>
