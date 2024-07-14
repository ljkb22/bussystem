<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>出行公交换乘查询</title>
    <style>
        html, body, #container {
            width: 100%;
            height: 100%;
        }
        #searchForm {
            position: absolute;
            top: 10px;
            left: 10px;
            z-index: 100;
            background-color: #ffffff;
            padding: 10px;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
        }
        #searchForm input, #searchForm button {
            margin: 5px;
        }
    </style>
</head>
<body>
<div id="container"></div>
<div id="searchForm">
    <input type="text" id="startPoint" placeholder="输入起点" />
    <input type="text" id="endPoint" placeholder="输入终点" />
    <button onclick="performSearch()">查询换乘</button>
</div>

<script type="text/javascript">
    window._AMapSecurityConfig = {
        securityJsCode: "fbfeef9b1c8afb497379ea26ad704a6a",
    };
</script>
<script src="https://webapi.amap.com/loader.js"></script>
<script type="text/javascript">
    AMapLoader.load({
        key: "6188b415301d5c73acbcd111b1bd7554",
        version: "2.0",
    })
        .then((AMap) => {
            var map = new AMap.Map('container', {
                resizeEnable: true,
                zoom: 11,
                center: [116.397428, 39.90923]
            });

            AMap.plugin('AMap.Transfer', function() {
                var transfer = new AMap.Transfer({
                    city: '南昌',
                    policy: AMap.TransferPolicy.NO_SUBWAY,
                    map: map,
                    panel: 'container',
                    showPolicy: true
                });

                window.performSearch = function() {
                    var start = document.getElementById('startPoint').value;
                    var end = document.getElementById('endPoint').value;

                    transfer.search([
                        {keyword: start, city: '南昌'},
                        {keyword: end, city: '南昌'}
                    ], function(status, result) {
                        if (status === 'complete' && result.info === 'OK') {
                            console.log(result);
                        } else {
                            console.error(result.info);
                        }
                    });
                };
            });
        });
</script>
</body>
</html>