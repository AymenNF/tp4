<template>
  <div class="book-space-cruise view-box">
    <h1>Book a Space Cruise</h1>
    <form @submit.prevent="bookSpaceCruise">
      <div>
        <label for="cabinType">Cabin Type:</label>
        <select
            id="cabinType"
            v-model="cabinType"
            required
        >
          <option value="STANDARD">STANDARD</option>
          <option value="DELUXE">DELUXE</option>
          <option value="SUITE">SUITE</option>
        </select>
      </div>

      <div>
        <label for="bookingDateTime">Booking Date & Time:</label>
        <input
            id="bookingDateTime"
            v-model="bookingDateTime"
            type="datetime-local"
            placeholder="Enter booking date & time"
            required
        />
      </div>

      <div>
        <label for="accountUsername">Account Username:</label>
        <input
            id="accountUsername"
            v-model="accountUsername"
            type="text"
            placeholder="Enter account username"
            required
        />
      </div>

      <div v-for="(traveler, index) in travelers" :key="index">
        <h3>Traveler {{ index + 1 }}</h3>
        <div>
          <label for="travelerName">Name:</label>
          <input
              id="travelerName"
              v-model="traveler.name"
              type="text"
              placeholder="Enter traveler's name"
              required
          />
        </div>
        <div>
          <label for="travelerCategory">Category:</label>
          <select
              id="travelerCategory"
              v-model="traveler.category"
              required
          >
            <option value="ADULT">ADULT</option>
            <option value="CHILD">CHILD</option>
            <option value="SENIOR">SENIOR</option>
          </select>
        </div>
      </div>

      <button type="button" @click="addTraveler">Add Traveler</button>
      <button type="submit">Book Space Cruise</button>
    </form>

    <div v-if="popupMessage" class="popup" :class="popupType">
      {{ popupMessage }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from "axios";

type Traveler = {
  name: string;
  category: string;
};

const cabinType = ref<string>('STANDARD');
const bookingDateTime = ref<string>('');
const accountUsername = ref<string>('');
const travelers = ref<Traveler[]>([]);
const popupMessage = ref<string | null>(null);
const popupType = ref<'success' | 'error' | ''>('');

const bookSpaceCruise = async (): Promise<void> => {
  try {
    await axios.post('/cruises/JUPITER_MOONS_EXPLORATION_2085/bookings', {
      cabinType: cabinType.value,
      bookingDateTime: bookingDateTime.value,
      accountUsername: accountUsername.value,
      travelers: travelers.value,
    });

    popupMessage.value = 'Space cruise successfully booked!';
    popupType.value = 'success';
    resetForm();

  } catch (error: any) {
    console.error('Failed to book space cruise', error);
    popupType.value = 'error';
    if (error.response.data.error === "ACCOUNT_NOT_FOUND") {
      popupMessage.value = 'Account username does not exist';
    } else if (error.response.data.error === "NO_TRAVELER") {
      popupMessage.value = 'At least one traveler is required';
    } else {
      popupMessage.value = 'Failed to book space cruise';
    }
  }

  setTimeout(() => {
    popupMessage.value = null;
  }, 3000);
}

const resetForm = (): void => {
  cabinType.value = '';
  bookingDateTime.value = '';
  accountUsername.value = '';
  travelers.value = [];
};

const addTraveler = (): void => {
  travelers.value.push({ name: '', category: 'ADULT' });
};
</script>
