//存放主要交互逻辑js代码
var seckill = {
    //秒杀相关地址
    URL : {
        now : function(){
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution : function (seckillId, md5){
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    //验证手机号
    validatePhone : function(phone){
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    //执行秒杀逻辑
    handleSeckill : function(seckillId, node){
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function(result){
            if(result && result['success']){
                var exposer = result['data'];
                //秒杀是否开启
                if(exposer['exposed']){
                    //开启秒杀
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        //1.禁用按钮
                        $(this).addClass('disabled');
                        //2.发送请求
                        $.post(killUrl, {}, function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+ stateInfo +'</span>');
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开始秒杀(客户端时间可能会比服务器快)
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算几时
                    seckill.countdown(seckillId, now, start, end)
                }
            }else{
                console.log(result);
            }
        })
    },
    //秒杀时间验证
    countdown : function(seckillId, nowTime, startTime, endTime){
        var seckillBox = $('#seckill-box');
        //时间判断
        if(nowTime > endTime){
            //秒杀结束
            seckillBox.html('秒杀结束');
        }else if(nowTime < startTime){
            //秒杀未开始,计时时间绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时: %D天 %H小时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function(){
                //时间完成后回调
                seckill.handleSeckill(seckillId, seckillBox);
            });
        }else {
            //秒杀开始
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },
    //秒杀逻辑
    detail : {
        //详情页初始化
        init : function(params){
            //手机验证和登录,计时交互

            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                //显示弹出层
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show : true,//显示弹出层
                    backdrop : 'static',//禁止位置关闭
                    keyboard : false //关闭键盘事件
                })
                //绑定手机号
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhone').val();
                    if(seckill.validatePhone(inputPhone)){
                        //写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }
            //已经登录
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function(result){
                if(result && result['success']){
                    var nowTime = result['data'];
                    //时间判断
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                }else{
                    console.log(result);
                }
            });

        }
    }
}