<!DOCTYPE html>

<html lang="en">
<head>
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style type="text/css">
        body{
            margin: 0;
            padding:0;
            border:0;
        }
    </style>
</head>
<body>
    <#if pdfUrl?contains("http://") || pdfUrl?contains("https://")>
        <#assign finalUrl="${pdfUrl}">
    <#else>
        <#assign finalUrl="${baseUrl}${pdfUrl}">
    </#if>
    <iframe src="" width="100%" frameborder="0"></iframe>

<#--    <img src="images/left.png" style="position: fixed; cursor: pointer; top: 40%; right: 60px; z-index: 999;" alt="使用图片预览" title="使用图片预览" onclick="goForImage()"/>-->
    <span class="fa fa-file-image-o fa-4x" style="position: fixed; cursor: pointer; top: 40%; right: 50px; z-index: 999;" title="使用图片预览" onclick="goForImage()"></span>


</body>
<script type="text/javascript">
    <#--document.getElementsByTagName('iframe')[0].src = "/pdfjs/web/viewer.html?file="+encodeURIComponent('${finalUrl}');-->

    var contextPath = getRootPath();
    if (contextPath != null && contextPath !== "/") {
        console.log(contextPath)
        document.getElementsByTagName('iframe')[0].src = "/" + contextPath + "/pdfjs/web/viewer.html?file=" + encodeURIComponent('${finalUrl}');
    } else {
        document.getElementsByTagName('iframe')[0].src = "/pdfjs/web/viewer.html?file=" + encodeURIComponent('${finalUrl}');
    }

    document.getElementsByTagName('iframe')[0].height = document.documentElement.clientHeight-10;
    /**
     * 页面变化调整高度
     */
    window.onresize = function(){
        var fm = document.getElementsByTagName("iframe")[0];
        fm.height = window.document.documentElement.clientHeight-10;
    }

    function goForImage() {
        var url = window.location.href;
        if (url.indexOf("officePreviewType=pdf") != -1) {
            url = url.replace("officePreviewType=pdf", "officePreviewType=image");
        } else {
            url = url + "&officePreviewType=image";
        }
        window.location.href=url;
    }

    function getRootPath() {
        //获取当前网址，如：http://localhost:8080/supermarket/user.do?method=query
        var currentWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如：/supermarket/user.do(注意：不包括?后面传递的参数)
        var pathName = window.document.location.pathname;

        //获取/uimcardprj中/的位置(也就是主机地址后面的/)，这里是：21
        var position = currentWwwPath.indexOf(pathName);

        //获取主机地址，如：http://localhost:8080
        var localhostPath = currentWwwPath.substring(0, position);


        //获取带"/"的项目名，如：/supermarket 
        //   /supermarket(0是为了把/也截取出来)
        var projectName = pathName.substring(1, pathName.substr(1).indexOf('/') + 1);
        //alert(pathName.substr(1).indexOf('/')); //这里是11，最后用substring截取字符串时，不包括第二个参数，所以要+1
        //pathName.substr(1)的结果是：supermarket/user.do
        //pathName.substr(1).indexOf('/')的结果是：supermarket的长度，就是11
        //indexOf('/')指的是字符串/首次出现的位置

        path = projectName;
        return path;
    }
</script>
</html>