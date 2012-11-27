function listView(element) {
    var elementClass = element.getAttribute("class");
    if (elementClass.indexOf("first") != -1) {
        var childs = element.parentNode.getElementsByTagName("div");
        for (var i = 0; i < childs.length; i++) {
            if (childs[i].style.display == "block" && childs[i].getAttribute("class").indexOf("first") == -1) {
                hideChild(childs[i]);
            } else {
                if (childs[i].getAttribute("class").indexOf("second") != -1) {
                    showChild(childs[i]);
                }
            }
        }
    } else if (elementClass.indexOf("second") != -1) {
        var childs = element.parentNode.getElementsByTagName("div");
        for (var i = 0; i < childs.length; i++) {
            if (childs[i].style.display == "block" && childs[i].getAttribute("class").indexOf("second") == -1) {
                hideChild(childs[i]);
            } else {
                if (childs[i].getAttribute("class").indexOf("third") != -1) {
                    showChild(childs[i]);
                }
            }
        }
    } else if (elementClass.indexOf("third") != -1) {
        var childs = element.parentNode.getElementsByTagName("div");
        for (var i = 0; i < childs.length; i++) {
            if (childs[i].style.display == "block" && childs[i].getAttribute("class").indexOf("third") == -1) {
                hideChild(childs[i]);
            } else {
                if (childs[i].getAttribute("class").indexOf("forth") != -1) {
                    showChild(childs[i]);
                }
            }
        }
    } else if (elementClass.indexOf("forth") != -1) {
        var childs = element.parentNode.getElementsByTagName("div");
        for (var i = 0; i < childs.length; i++) {
            if (childs[i].style.display == "block" && childs[i].getAttribute("class").indexOf("forth") == -1) {
                hideChild(childs[i]);
            } else {
                if (childs[i].getAttribute("class").indexOf("fifth") != -1) {
                    showChild(childs[i]);
                }
            }
        }
    } else if (elementClass.indexOf("fifth") != -1) {
        var childs = element.parentNode.getElementsByTagName("div");
        for (var i = 0; i < childs.length; i++) {
            if (childs[i].style.display == "block" && childs[i].getAttribute("class").indexOf("fifth") == -1) {
                hideChild(childs[i]);
            } else {
                if (childs[i].getAttribute("class").indexOf("sixth") != -1) {
                    showChild(childs[i]);
                }
            }
        }
    } else if (elementClass.indexOf("sixth") != -1) {
        var childs = element.parentNode.getElementsByTagName("div");
        for (var i = 0; i < childs.length; i++) {
            if (childs[i].style.display == "block" && childs[i].getAttribute("class").indexOf("sixth") == -1) {
                hideChild(childs[i]);
            } else {
                if (childs[i].getAttribute("class").indexOf("seventh") != -1) {
                    showChild(childs[i]);
                }
            }
        }
    }
}
function showChild(element) {
    element.style.display = "block";
}
function hideChild(element) {
    element.style.display = "none";
}
function setColor() {
    var rootUL = document.getElementById("steps");
    var uls = rootUL.getElementsByTagName("ul");
    for (var i = 0; i < uls.length; i++) {
        var firstDiv = uls[i].getElementsByTagName("div")[0];
        firstDiv.setAttribute("class", firstDiv.getAttribute("class") + " menu");
    }
    var divs = rootUL.getElementsByTagName("div");
    for (var i = 0; i < divs.length; i++) {
        if (divs[i].getAttribute("class").indexOf("first") == -1) {
            if (divs[i].getAttribute("title").indexOf("INFO") != -1) {
                divs[i].setAttribute("class", divs[i].getAttribute("class") + " green");
            } else if (divs[i].getAttribute("title").indexOf("ERROR") != -1) {
                divs[i].setAttribute("class", divs[i].getAttribute("class") + " red");
            } else {
                divs[i].setAttribute("class", divs[i].getAttribute("class") + " yellow");
            }
            var screenshot = divs[i].getElementsByTagName("span")[1].innerHTML;
            if (screenshot.indexOf("currentscreen") != -1) {
                divs[i].style.textDecoration = "underline";
            }
        }
    }
    setHierarchyColor();
    document.getElementById("infoStackTrace").style.height = (window.innerHeight - 350) + "px";
}
function setHierarchyColor() {
    var rootUL = document.getElementById("steps");
    var uls = rootUL.getElementsByTagName("ul");
    for (var i = 0; i < uls.length; i++) {
        var divs = uls[i].getElementsByTagName("div");
        for (var j = 0; j < divs.length; j++) {
            if (divs[j].getAttribute("title") != null) {
                if (divs[j].getAttribute("title").indexOf("ERROR") != -1) {
                    if (uls[i].getElementsByTagName("div")[0].getAttribute("class").indexOf("green") != -1) {
                        var newClass = uls[i].getElementsByTagName("div")[0].getAttribute("class");
                        newClass = newClass.replace("green", "red");
                        uls[i].getElementsByTagName("div")[0].setAttribute("class", newClass);
                        break;
                    } else if (uls[i].getElementsByTagName("div")[0].getAttribute("class").indexOf("yellow") != -1) {
                        var newClass = uls[i].getElementsByTagName("div")[0].getAttribute("class");
                        newClass = newClass.replace("yellow", "red");
                        uls[i].getElementsByTagName("div")[0].setAttribute("class", newClass);
                        break;
                    }
                } else if (divs[j].getAttribute("title").indexOf("WARN") != -1) {
                    if (uls[i].getElementsByTagName("div")[0].getAttribute("class").indexOf("green") != -1) {
                        var newClass = uls[i].getElementsByTagName("div")[0].getAttribute("class");
                        newClass = newClass.replace("green", "yellow");
                        uls[i].getElementsByTagName("div")[0].setAttribute("class", newClass);
                    }
                }
            }
        }
    }
}
function showInfo(element, textInfo) {
    element = element.parentNode;
    var time = "";
    var type = "";
    var thread = "";
    var method = "";
    var text = "";
    var screen = "";
    var stackTrace = "";
    if (textInfo.indexOf(" INFO ") != -1) {
        time = textInfo.substring(0, 12);
        type = textInfo.substring(13, 18);
        thread = textInfo.substring(19, 23);
        method = textInfo.substring(24, textInfo.indexOf(" ", 24));
        text = textInfo.substring(textInfo.indexOf("-") + 2, textInfo.length);
        screen = element.getElementsByTagName("span")[1].innerHTML;
        stackTrace = element.getElementsByTagName("span")[0].innerHTML;
    } else if (textInfo.indexOf(" WARN ") != -1) {
        time = textInfo.substring(0, 12);
        type = textInfo.substring(13, 18);
        thread = textInfo.substring(19, 23);
        method = textInfo.substring(24, textInfo.indexOf(" ", 24));
        text = textInfo.substring(textInfo.indexOf("-") + 2, textInfo.length);
        screen = element.getElementsByTagName("span")[1].innerHTML;
        stackTrace = element.getElementsByTagName("span")[0].innerHTML;
    } else if (textInfo.indexOf(" ERROR ") != -1) {
        time = textInfo.substring(0, 12);
        type = textInfo.substring(13, 18);
        thread = textInfo.substring(19, 23);
        method = textInfo.substring(24, textInfo.indexOf(" ", 24));
        text = textInfo.substring(textInfo.indexOf("-") + 2, textInfo.length);
        screen = element.getElementsByTagName("span")[1].innerHTML;
        stackTrace = element.getElementsByTagName("span")[0].innerHTML;
    }
    document.getElementById("infoTime").innerHTML = time;
    document.getElementById("infoType").innerHTML = type;
    document.getElementById("infoThread").innerHTML = thread;
    document.getElementById("infoMethod").innerHTML = method;
    document.getElementById("infoText").innerHTML = text;
    document.getElementById("infoScreenShot").innerHTML = screen;
    document.getElementById("infoStackTrace").innerHTML = removeEnters(stackTrace);
}
function removeEnters(text) {
    var stackTrace = "";
    var rows = text.split("\n");
    for (var i = 0; i < rows.length; i++) {
        stackTrace += rows[i];
    }
    return stackTrace;
}
function showScreen(screenShotPath) {
    document.getElementById("globalScreenShot").getElementsByTagName("img")[0].src = "screenshots/" + screenShotPath;
    document.getElementById("globalScreenShot").style.display = "block";
}
function hideScreen() {
    document.getElementById("globalScreenShot").getElementsByTagName("img")[0].src = "";
    document.getElementById("globalScreenShot").style.display = "none";
}