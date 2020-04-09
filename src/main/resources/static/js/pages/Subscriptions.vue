<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list>
                <v-list-item v-for="item in subscriptions">
                    <user-link :user="item.subscriber" size="24"></user-link>
                    <v-btn @click="changeSubscriptionsStatus(item.subscriber.id)">{{item.active ? "Dismiss" : "Approve"}}</v-btn>
                </v-list-item>
            </v-list>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile';
    import UserLink from "components/UserLink.vue";

    export default {
        name: "Subscriptions",
        components: {UserLink},
        data() {
            return {
                subscriptions: []
            }
        },
        methods: {
            async changeSubscriptionsStatus(subscriberId) {
                await profileApi.changeSubscriptionsStatus(subscriberId);

                const subscriptionIndex = this.subscriptions.findIndex(item => item.subscriber.id === subscriberId);
                const subscription = this.subscriptions[subscriptionIndex];

                this.subscriptions = [
                    ...this.subscriptions.slice(0, subscriptionIndex),
                    {
                        ...subscription,
                        active: !subscription.active
                    },
                    ...this.subscriptions.slice(subscriptionIndex + 1)
                ];
            }
        },
        async beforeMount() {
            const response = await profileApi.subscriberList(this.$store.state.profile.id);
            this.subscriptions = await response.json();
        }
    }
</script>

<style scoped>

</style>