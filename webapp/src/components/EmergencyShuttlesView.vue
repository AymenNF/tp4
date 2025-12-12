<template>
  <div class="emergency-shuttles-manifest view-box" id="emergency-shuttles-manifest">
    <h1 id="heading">Emergency Shuttles Manifest</h1>
    <button @click="fetchManifest" id="fetch-manifest-button" data-cy="fetch-emergency-shuttles-manifest" :class="{ 'red-button': emptyManifestObtained }">Get Manifest</button>

    <div v-if="manifest.emergencyShuttles.length" id="manifest-details">
      <h2 id="cost-heading">Total Cost: </h2> <h2 data-cy="total-cost">{{ manifest.cost }}</h2>
      <table>
        <thead>
        <tr>
          <th>Shuttle Type</th>
          <th>Travelers</th>
          <th>Crew Members</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(shuttle, index) in manifest.emergencyShuttles" :key="index" :id="'shuttle-' + index" data-cy="emergency-shuttle">
          <td>{{ shuttle.type }}</td>
          <td>
            <table>
              <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="traveler in shuttle.travelers" :key="traveler.travelerId" :id="'traveler-' + traveler.travelerId">
                <td>{{ traveler.travelerId }}</td>
                <td>{{ traveler.travelerName }}</td>
              </tr>
              </tbody>
            </table>
          </td>
          <td>
            <table>
              <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="crewMember in shuttle.crewMembers" :key="crewMember.employeeId" :id="'crewMember-' + crewMember.employeeId">
                <td>{{ crewMember.employeeId }}</td>
                <td>{{ crewMember.crewMemberName }}</td>
              </tr>
              </tbody>
            </table>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>


<script setup lang="ts">
import { ref } from 'vue';
import axios from "axios";

type Manifest = {
  cost: number;
  emergencyShuttles: {
    type: string;
    travelers: {
      travelerId: string;
      travelerName: string;
    }[];
    crewMembers: {
      employeeId: string;
      crewMemberName: string;
    }[];
  }[];
};

const manifest = ref<Manifest>({ cost: 0, emergencyShuttles: [] });
const emptyManifestObtained = ref<boolean>(false);

const fetchManifest = async (): Promise<void> => {
  try {
    const response = await axios.get<Manifest>('/cruises/JUPITER_MOONS_EXPLORATION_2085/emergencyShuttles');
    console.log('Fetched emergency shuttles manifest:', response.data);

    manifest.value = response.data;
    emptyManifestObtained.value = !response.data.emergencyShuttles.length;

  } catch (error) {
    console.error('Failed to fetch emergency shuttles manifest', error);
  }
};
</script>

<style scoped>
button.red-button {
  background-color: #ff0000;
}
</style>

