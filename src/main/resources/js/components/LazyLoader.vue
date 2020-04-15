<template>
    <span></span>
</template>

<script>
    import {mapActions} from 'vuex';

    export default {
        name: "LazyLoader",
        methods: mapActions(['loadPageAction']),
        mounted() {
            window.onscroll = () => {
                const el = document.documentElement;
                const bottomOnScreen = el.scrollTop + window.innerHeight === el.offsetHeight;

                if (bottomOnScreen) {
                    this.loadPageAction();
                }
            }
        },
        beforeDestroy() {
            window.onscroll = null;
        }
    }
</script>

<style scoped>

</style>