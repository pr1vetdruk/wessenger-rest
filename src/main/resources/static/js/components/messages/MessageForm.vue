<template>
    <div>
        <input type="text" placeholder="Write text message" v-model="text"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
    import {sendMessage} from "util/ws";

    export default {
        props: ['messages', 'message'],
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
            save() {
                sendMessage({id: this.id, text: this.text});
                this.id = '';
                this.text = '';
            }
        }
    }
</script>

<style scoped>

</style>