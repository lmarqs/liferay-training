import Vue from 'vue/dist/vue.common';

export default (namespace, AUI, $, _) => {

    AUI().use("aui-datepicker", function (A) {
        // console.log('oi', A, $(".aui-datepicker"));

        new A.DatePicker({
            trigger: ".aui-datepicker",
            mask: "%m/%d/%Y",
            popover: {
                zIndex: 1000
            }
        });

    });

    // Vue.directive('aui-datepicker', (el, binding, vnode, oldVnode) => {
    //
    // });

    // const app = new Vue({
    //     el: `#${namespace}-content`
    // });

}