$(document).ready(function() {  
            //bootbox.setDefaults({  
                /**  
                * @optional String  
                * @default: en  
                * which locale settings to use to translate the three  
                * standard button labels: OK, CONFIRM, CANCEL  
                */  
                //locale: "fr",  
                /**  
                * @optional Boolean  
                * @default: true  
                * whether the dialog should be shown immediately  
                */  
                //show: true,  
                /**  
                * @optional Boolean  
                * @default: true  
                * whether the dialog should be have a backdrop or not  
                */  
                //backdrop: true,  
                /**  
                * @optional Boolean  
                * @default: true  
                * show a close button  
                */  
                //closeButton: true,  
                /**  
                * @optional Boolean  
                * @default: true  
                * animate the dialog in and out (not supported in < IE 10)  
                */  
                //animate: true,  
                /**  
                * @optional String  
                * @default: null  
                * an additional class to apply to the dialog wrapper  
                */  
                //className: "my-modal"  
            //});   
        });  
      
      
        $(document).on("click", "#test", function (e) {  
            bootbox.confirm("Hello world!", function (result) {  
                if(result) {  
                    alert('点击了确认按钮');  
                } else {  
                    alert('点击了取消按钮');  
                }  
            });  
            bootbox.dialog({  
                message: "I am a custom confirm",  
                title: "Confirm title",  
                buttons: {  
                    Cancel: {  
                        label: "Cancel",  
                        className: "btn-default",  
                        callback: function () {  
                            alert("Cancel");  
                        }  
                    }  
                    , OK: {  
                        label: "OK",  
                        className: "btn-primary",  
                        callback: function () {  
                            alert("OK");  
                        }  
                    }  
                }  
            });  
              
        bootbox.confirm({  
        buttons: {  
            confirm: {  
                label: '我是确认按钮',  
                className: 'btn-myStyle'  
            },  
            cancel: {  
                label: '我是取消按钮',  
                className: 'btn-default'  
            }  
        },  
        message: '提示信息',  
        callback: function(result) {  
            if(result) {  
                alert('点击确认按钮了');  
            } else {  
                alert('点击取消按钮了');  
            }  
        },  
        //title: "bootbox confirm也可以添加标题哦",  
        });
        bootbox.alert({  
            buttons: {  
               ok: {  
                    label: '我是ok按钮',  
                    className: 'btn-myStyle'  
                }  
            },  
            message: '提示信息',  
            callback: function() {  
                alert('关闭了alert');  
            },  
            title: "bootbox alert也可以添加标题哦",  
        });  
       });  
          