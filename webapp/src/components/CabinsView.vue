<template>
  <div class="cabin-attributions view-box">
    <h1>Cabin Attributions</h1>
    <div>
      <label for="criteria">Attribution Criteria:</label>
      <select id="criteria" v-model="criteria">
        <option value="bookingDateTime">Par ordre de date de réservation</option>
        <option value="travelers">Par nombre de voyageurs, puis par ordre de date de réservation</option>
      </select>
    </div>
    <button @click="fetchCabinAttributions" data-cy="fetch-cabin-attribution">Get Cabin Attributions</button>

    <div v-if="cabins.length">
      <h2>Cabins</h2>
      <table >
        <thead>
        <tr>
          <th>Cabin ID</th>
          <th>Category</th>
          <th>Booking ID</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="cabin in cabins" :key="cabin.cabinId" class="cabin-row" data-cy="cabin-attribution-details">
          <td class="cabin-id-item">{{ cabin.cabinId }}</td>
          <td class="cabin-category-item">{{ cabin.category }}</td>
          <td class="cabin-bookingId-item">{{ cabin.bookingId }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from "axios";

type Cabin = {
  cabinId: string;
  category: string;
  bookingId: string;
};

const cabins = ref<Cabin[]>([]);
const criteria = ref<string>('bookingDateTime');

const fetchCabinAttributions = async (): Promise<void> => {
  try {
    const response = await axios.get<{cabins: Cabin[]}>('/cruises/JUPITER_MOONS_EXPLORATION_2085/cabins', {
      params: {
        criteria: criteria.value,
      },
    });

    cabins.value = response.data.cabins;
    console.log('Fetched cabin attributions:', cabins.value);

  } catch (error: any) {
    console.error('Failed to fetch cabin attributions', error);
  }
}
</script>
