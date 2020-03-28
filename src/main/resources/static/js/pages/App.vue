<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Wessenger-REST</v-toolbar-title>

            <v-spacer></v-spacer>

            <v-btn icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
            {{profile.name}}
        </v-app-bar>
        <v-content>
            <div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>
            <div><messages-list :messages="messages"/></div>
        </v-content>
    </v-app>
</template>

<script>
    import MessagesList from "components/messages/MessageList.vue";
    import { addHandler } from "util/ws";
    import  { getIndex } from "util/collections";

    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.messages, data.id);
                if (index > -1) {
                    this.messages.splice(index, 1, data)
                } else {
                    this.messages.push(data)
                }
            })
        }
    }
</script>

<style scoped>
</style>