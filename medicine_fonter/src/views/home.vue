<template>
    <div class="dashboard-container">
        <div class="dashboard-editor-container">
            <panel-group />
            <!--<el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">-->
                <!--<line-chart />-->
                <!--<pie-chart />-->
            <!--</el-row>-->

            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="12">
                    <div class="chart-wrapper">
                        <line-chart />
                    </div>
                </el-col>
                <el-col :xs="24" :sm="24" :lg="12">
                    <div class="chart-wrapper">
                        <pie-chart />
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    import PanelGroup from './dashboard/PanelGroup'
    import LineChart from './dashboard/LineChart'
    import PieChart from './dashboard/PieChart'




    export default {
        name: 'Dashboard',
        components: {
            PanelGroup,
            LineChart,
            PieChart,
        },
        computed: {
            ...mapGetters([
                'roles'
            ])
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .dashboard-editor-container {
        padding: 18px 22px 22px 22px;
        background-color: rgb(240, 242, 245);
        .chart-wrapper {
            background: #fff;
            padding: 16px 16px 0;
            margin-bottom: 32px;
        }
    }
</style>
