import Vue from 'vue/dist/vue.common';

export default (namespace, AUI, $, _) => {

    AUI().use("aui-datepicker", function (A) {
        new A.DatePicker({
            trigger: ".aui-datepicker",
            mask: "%m/%d/%Y",
            popover: {
                zIndex: 1000
            }
        });

    });

}