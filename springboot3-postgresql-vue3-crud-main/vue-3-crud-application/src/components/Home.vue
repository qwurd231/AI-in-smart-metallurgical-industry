<template>
    <div class="container">
        <br><br><br>
        <div class="list-group" style="align-items: center;">
            <router-link to="/users" class="list-group-item list-group-item-action">
                用户
            </router-link>
            <br><br><br>
            <router-link to="/login" class="list-group-item list-group-item-action">
                登录
            </router-link>
            <br><br><br>
            <router-link to="/register" class="list-group-item list-group-item-action">
                注册
            </router-link>
        </div>
    </div>
</template>

<script>
// implement login and logout methods
export default {
    name: "Home",
};
</script>

<style scoped>
.list-group-item {
    width: 200px;
    height: 80px;
    font-size: 25px;
    text-align: center;
    border-radius: 10px;
    align-items: center;
    background-color: #bfe7ec;
}

.list-group-item:hover {
    background-color: #7ce8a1;
}
</style>