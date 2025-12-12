import './assets/main.css'

import { createApp } from 'vue'
import axios from 'axios'
import App from './App.vue'

axios.defaults.baseURL = 'http://127.0.0.1:8181' // L'URL du serveur de l'api serait normalement dans une variable d'environnement (ex: dans le fichier .env)
createApp(App).mount('#app')
