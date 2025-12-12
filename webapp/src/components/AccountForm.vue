<template>
  <div class="create-account view-box">
    <h1>Create an Account</h1>
    <form @submit.prevent="createAccount">
      <div>
        <label for="username">Username:</label>
        <input
            id="username"
            v-model="username"
            type="text"
            placeholder="Enter username"
            required
        />
      </div>

      <div>
        <label for="homePlanet">Home Planet:</label>
        <input
            id="homePlanet"
            v-model="homePlanet"
            type="text"
            placeholder="Enter home planet"
            required
        />
      </div>

      <button type="submit">Create Account</button>
    </form>

    <div v-if="popupMessage" class="popup" :class="popupType">
      {{ popupMessage }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from "axios";

const username = ref<string>('');
const homePlanet = ref<string>('');
const popupMessage = ref<string | null>(null);
const popupType = ref<'success' | 'error' | ''>('');

const createAccount = async (): Promise<void> => {
  try {
    await axios.post('/accounts', {
      username: username.value,
      homePlanetName: homePlanet.value,
    });

    popupMessage.value = 'Account successfully created!';
    popupType.value = 'success';
    resetForm();

  } catch (error: any) {
    console.error('Failed to create account', error);
    popupType.value = 'error';
    if (error.response.data.error === "USERNAME_ALREADY_EXISTS") {
      popupMessage.value = 'Username already exists';
    } else {
      popupMessage.value = 'An error occurred while creating the account';
    }
  }

  setTimeout(() => {
    popupMessage.value = null;
  }, 3000);
}

const resetForm = (): void => {
  username.value = '';
  homePlanet.value = '';
};
</script>
