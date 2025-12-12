<template>
  <div class="create-crew-member view-box">
    <h1>Create a Crew Member</h1>
    <form @submit.prevent="createCrewMember">
      <div>
        <label for="employeeId">Employee ID:</label>
        <input
            id="employeeId"
            v-model="employeeId"
            type="text"
            placeholder="Enter employee ID"
            required
        />
      </div>

      <div>
        <label for="name">Name:</label>
        <input
            id="name"
            v-model="name"
            type="text"
            placeholder="Enter name"
            required
        />
      </div>

      <button type="submit">Create Crew Member</button>
    </form>

    <div v-if="popupMessage" class="popup" :class="popupType">
      {{ popupMessage }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from "axios";

const employeeId = ref<string>('');
const name = ref<string>('');
const popupMessage = ref<string | null>(null);
const popupType = ref<'success' | 'error' | ''>('');

const createCrewMember = async (): Promise<void> => {
  try {
    await axios.post('/cruises/JUPITER_MOONS_EXPLORATION_2085/crewMembers', {
      employeeId: employeeId.value,
      name: name.value,
    });

    popupMessage.value = 'Crew member successfully created!';
    popupType.value = 'success';
    resetForm();

  } catch (error: any) {
    console.error('Failed to create crew member', error);
    popupType.value = 'error';
    if (error.response.data.error === "INVALID_EMPLOYEE_ID_FORMAT") {
      popupMessage.value = 'Employee ID must be in the format ABC123';
    }
    else if (error.response.data.error === "CREW_MEMBER_ALREADY_EXISTS") {
      popupMessage.value = 'Crew member with employee ID already exists';
    }
    else {
      popupMessage.value = 'Failed to create crew member';
    }
  }

  setTimeout(() => {
    popupMessage.value = null;
  }, 3000);
};

const resetForm = (): void => {
  employeeId.value = '';
  name.value = '';
};
</script>
