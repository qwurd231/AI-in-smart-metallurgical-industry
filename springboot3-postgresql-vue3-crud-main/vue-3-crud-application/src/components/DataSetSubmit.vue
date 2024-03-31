<template>
    <div class="container">
        <h3>数据</h3>
        <div class="container">



            <form @submit="validateAndSubmit">
                <div class="form-row">
                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>铁水质量(吨)</label>
                            <input type="text" class="form-control" v-model="param1" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>废钢质量(吨)</label>
                            <input type="text" class="form-control" v-model="param2" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>生铁质量(吨)</label>
                            <input type="text" class="form-control" v-model="param3" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>白云石质量(吨)</label>
                            <input type="text" class="form-control" v-model="param4" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>铁水硅含量</label>
                            <input type="text" class="form-control" v-model="param5" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>矿石质量(吨)</label>
                            <input type="text" class="form-control" v-model="param6" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>铁水温度(℃)</label>
                            <input type="text" class="form-control" v-model="param7" style="width: 140px;"/>
                        </fieldset>
                    </div>

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>铁水碳含量</label>
                            <input type="text" class="form-control" v-model="param8" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>吹氧时间(秒)</label>
                            <input type="text" class="form-control" v-model="param9" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>石灰石质量(千克)</label>
                            <input type="text" class="form-control" v-model="param10" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>铁水带渣量(吨)</label>
                            <input type="text" class="form-control" v-model="param11" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>余渣量(吨)</label>
                            <input type="text" class="form-control" v-model="param12" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>溅渣时间(秒)</label>
                            <input type="text" class="form-control" v-model="param13" style="width: 140px;"/>
                        </fieldset>
                    </div>
                    &nbsp;&nbsp;&nbsp;

                    <div style="text-align: center;">
                        <fieldset class="form-group">
                            <label>耗氧量(立方米)</label>
                            <input type="text" class="form-control" v-model="param14" style="width: 140px;"/>
                        </fieldset>
                    </div>

                </div>
                <div style="text-align: center;">
                    <h4>模型</h4>
                    <select v-model="selectedModel">
                        <option>DNN</option>
                        <option>KNN</option>
                        <option>Random Forest</option>
                    </select>
                </div>
                <br>
                <div v-if="errors.length">
                    <div class="alert alert-danger" v-bind:key="index" v-for="(error, index) in errors">
                        {{ error }}
                    </div>
                </div>
                <div style="text-align: center;">
                    <button class="btn btn-success" v-on:click="addData()">提交</button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import DataSetService from "../service/DataSetService";

export default {
    name: "DataSetSubmit",
    data() {
        return {
            param1: "",
            param2: "",
            param3: "",
            param4: "",
            param5: "",
            param6: "",
            param7: "",
            param8: "",
            param9: "",
            param10: "",
            param11: "",
            param12: "",
            param13: "",
            param14: "",
            selectedModel: "",
            errors: [],
        };
    },
    computed: {
        id() {
            return Number(this.$route.params.data_id);
        },
        userId() {
            return Number(this.$route.params.user_id);
        },
    },
    methods: {
        validateAndSubmit(e) {
            e.preventDefault();

            this.errors = [];
            if (!this.param1 || !this.param2 || !this.param3 || !this.param4 || !this.param5 || !this.param6
                || !this.param7 || !this.param8 || !this.param9 || !this.param10 || !this.param11 || !this.param12
                || !this.param13 || !this.param14 || !this.selectedModel) {
                this.errors.push("There are null values");
            } else {

                const num = new RegExp("^-?([0-9]+\\.?)?[0-9]+$");
                if (num.test(this.param1) === false || num.test(this.param2) === false || num.test(this.param3) === false
                    || num.test(this.param4) === false || num.test(this.param5) === false || num.test(this.param6) === false
                    || num.test(this.param7) === false || num.test(this.param8) === false || num.test(this.param9) === false
                    || num.test(this.param10) === false || num.test(this.param11) === false
                    || num.test(this.param12) === false || num.test(this.param13) === false
                    || num.test(this.param14) === false) {
                    this.errors.push("There are non-numeric values");
                } else {
                    this.param1 = Number(this.param1)
                    this.param2 = Number(this.param2)
                    this.param3 = Number(this.param3)
                    this.param4 = Number(this.param4)
                    this.param5 = Number(this.param5)
                    this.param6 = Number(this.param6)
                    this.param7 = Number(this.param7)
                    this.param8 = Number(this.param8)
                    this.param9 = Number(this.param9)
                    this.param10 = Number(this.param10)
                    this.param11 = Number(this.param11)
                    this.param12 = Number(this.param12)
                    this.param13 = Number(this.param13)
                    this.param14 = Number(this.param14)

                    if (this.param1 <= 0 || Number(this.param2) <= 0 || Number(this.param3) <= 0 ||
                        Number(this.param4) <= 0 || Number(this.param5) <= 0 || Number(this.param6) <= 0 ||
                        Number(this.param7) <= 0 || Number(this.param8) <= 0 || Number(this.param9) <= 0 ||
                        Number(this.param10) <= 0 || Number(this.param11) <= 0 || Number(this.param12) <= 0
                        || Number(this.param13) <= 0 || Number(this.param14) <= 0) {
                        this.errors.push("There are non-positive values");
                    }
                }
            }

            console.log(this.errors)
            return this.errors.length === 0;
        },
        addData() {
            const isValid = this.validateAndSubmit(event);
            console.log(this.$route.params.data_id)
            if (isValid) {
                if (this.id === -1) {
                    DataSetService.createDataSet({
                        userId: this.userId,
                        param1: this.param1,
                        param2: this.param2,
                        param3: this.param3,
                        param4: this.param4,
                        param5: this.param5,
                        param6: this.param6,
                        param7: this.param7,
                        param8: this.param8,
                        param9: this.param9,
                        param10: this.param10,
                        param11: this.param11,
                        param12: this.param12,
                        param13: this.param13,
                        param14: this.param14,
                        selectedModel: this.selectedModel
                    }).then(() => {
                        this.$router.push(`/user/${this.userId}/data`);
                    });
                } else {
                    DataSetService.updateDataSet(this.id, {
                        id: this.id,
                        userId: this.userId,
                        param1: this.param1,
                        param2: this.param2,
                        param3: this.param3,
                        param4: this.param4,
                        param5: this.param5,
                        param6: this.param6,
                        param7: this.param7,
                        param8: this.param8,
                        param9: this.param9,
                        param10: this.param10,
                        param11: this.param11,
                        param12: this.param12,
                        param13: this.param13,
                        param14: this.param14,
                        selectedModel: this.selectedModel
                    }).then(() => {
                        this.$router.push(`/user/${this.userId}/data`);
                    });
                }
            }
        },
    },
};
</script>