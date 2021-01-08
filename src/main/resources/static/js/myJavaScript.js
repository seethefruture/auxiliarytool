function post(URL, PARAMS) {
    var temp = document.createElement("form");
    temp.action = URL;
    temp.method = "post";
    temp.style.display = "none";
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;
}

function postDownload(URL, PARAMS, FILENAME) {
    var temp = document.createElement("form");
    temp.action = URL;
    temp.download = FILENAME;
    temp.method = "post";
    temp.style.display = "none";
    temp.target = "_blank"
    for (var x in PARAMS) {
        var opt = document.createElement("input");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

function setCookie(cname, cvalue, exhours) {
    var d = new Date();
    d.setTime(d.getTime() + (exhours * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires+";path=/";
}

function delCookie(name) {
    setCookie(name,' ',-1);
    // if (getCookie(name) != null)
    //     document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
}

function rememberMe(username, password,type) {
    setCookie("type",type);
    setCookie("username", username);
    setCookie("password", password);
}

function checkCookieWhenLogin() {
    let data = {};
    let type = getCookie("type");
    let username = getCookie("username");
    let password = getCookie("password");
    if (username != null) {
        data.username = username;
    }
    if (password != null) {
        data.password = password;
    }
    if (type != null) {
        data.type = type;
    }
    return data;
}

function signOut() {
    delCookie("username");
    delCookie("password");
    delCookie("type");
}
