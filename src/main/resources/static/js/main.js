import Vue from 'vue';
import Vuetify from 'vuetify';
import '@babel/polyfill';
import 'api/resource';
import router from 'router/router';
import App from 'pages/App.vue';
import store from 'store/store';
import {connect} from './util/ws';
import 'vuetify/dist/vuetify.min.css';
import * as Sentry from '@sentry/browser';
import * as Integrations from '@sentry/integrations';

Sentry.init({
    dsn: 'https://251a628edf0843918561419b28d04d21@o377566.ingest.sentry.io/5199844',
    integrations: [new Integrations.Vue({Vue, attachProps: true})],
});

Sentry.configureScope(scope => scope.setUser({
    id: profile && profile.id,
    username: profile && profile.name
}));

if (profile) {
    connect();
}

Vue.use(Vuetify);

new Vue({
    el: '#app',
    store,
    router,
    vuetify: new Vuetify(),
    render: a => a(App)
});
