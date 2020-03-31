<template>
    <v-layout row>
        <v-text-field label="New message" placeholder="Write text message" v-model="text"/>
        <v-btn @click="save">Save</v-btn>
    </v-layout>
</template>

<script>
    import {mapActions} from 'vuex';

    export default {
        props: ['message'],
        data() {
            return {
                id: '',
                text: ''
            }
        },
        watch: {
            message(newVal, oldVal) {
                this.id = newVal.id;
                this.text = newVal.text;
            }
        },
        methods: {
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                const message = {
                    id: this.id,
                    text: this.text
                };

                if (this.id) {
                    this.updateMessageAction(message);
                } else {
                    this.addMessageAction(message);
                }

                this.id = '';
                this.text = '';
            }
        }
    }
</script>

<style scoped>

</style>