function onKeyPress(languageCode) {
    var key = window.event.keyCode;
    if (key == 13) {
        request(languageCode);
    }
    return false;
}

function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

function request() {
    var csrftoken = getCookie('csrftoken');
    data = {};
    if ($('#article :selected').attr('id') == "ignore"){
        return;
    }

    var url = '/api?id='+ $('#article :selected').text();


    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        //alert(this.status)
        if (this.readyState == 4 && this.status == 200) {
            //alert(this.responseText)
            var myArr = JSON.parse(this.responseText);
            var amr=myArr['amr'].split("<line-break>").join("\r\n");
            var result=myArr['result'].split("<line-break>").join("\r\n");

            // alert(result);
            document.getElementById("txtContent").value = myArr['content'];
            document.getElementById("txtAmr").value = amr;
            document.getElementById("txtRewrite").value = result;
        }
    };
    xmlhttp.open('POST', url, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xmlhttp.setRequestHeader('X-CSRFToken', csrftoken);
    xmlhttp.send(JSON.stringify(data));

}

