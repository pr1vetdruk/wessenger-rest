<template>
    <router-link :to="`/user/${user.id}`" class="px-3">
        <v-avatar v-if="user && user.picture" :size="`${size || 36}px`">
            <img :src="user.picture" :alt="user.name"/>
        </v-avatar>

        <v-avatar v-else color="indigo" :size="`${size || 36}px`">
            <v-icon dark>mdi-account-circle</v-icon>
        </v-avatar>

        <span>{{authorName}}</span>
    </router-link>
</template>

<script>
    export default {
        name: "UserLink",
        props: ['user', 'size'],
        computed: {
            authorName() {
                return this.user ? this.user.name : 'unknown';
            }
        }
    }
</script>

<style scoped>

</style>