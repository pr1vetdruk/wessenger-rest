<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Wessenger-REST</v-toolbar-title>

            <v-spacer></v-spacer>

            <span v-if="profile">
                {{profile.name}}
                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </span>
        </v-app-bar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import {mapState, mapMutations} from "vuex";
    import {addHandler} from "util/ws";

    export default {
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body);
                            break;
                        case 'UPDATE':
                            this.updateMessageMutation(data.body);
                            break;
                        case 'REMOVE':
                            this.removeMessageMutation(data.body);
                            break;
                            default:
                                console.error('Looks like the event type if unknown "${data.eventType}"');
                    }
                } else {
                    console.error('Looks like the object type if unknown "${data.objectType}"');
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth');
            }
        }
    }
</script>

<style scoped>
</style>