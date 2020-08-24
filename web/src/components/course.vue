<template>

        <div class="card rounded-lg border-0 shadow">
            <img class="img-fluid rounded-top mb-3" v-bind:src="course.image"/>
            <div class="card-body">
                <router-link v-bind:to="'/detail?id=' + course.id"> <h4 class="mb-0">{{course.name}}</h4></router-link>

                <p class="card-text">{{course.summary}}</p>
                <div class="text-muted">
                    <span >{{COURSE_LEVEL | optionKV(course.level)}}</span>
                    <span ><i style="padding-left: 2rem" class="fa fa-user" aria-hidden="true"></i>&nbsp;{{course.enroll}}</span>&nbsp;
                </div>
                <span class="float-right" style="color: red"><i class="fa fa-yen" aria-hidden="true"></i>&nbsp;{{course.price}}</span>&nbsp;

            </div>



        </div>

</template>

<script>
    export default {
        name: "course",
        props: {
            course: {}
        },
        data: function () {
            return {
                COURSE_LEVEL: COURSE_LEVEL
            }
        },
    }
</script>

<style scoped>

</style>