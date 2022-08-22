function showSideNav() {
    console.log("showSideNav");
    // document.querySelector("#nav-side").style.display = "flex";
    document.querySelector("#nav-side").classList.remove("navFadeIn");
    document.querySelector("#nav-side").classList.add("navFadeOut");
    // document.querySelector("#nav-button").setAttribute("data-display", "1");
    // navBarShowFlag = true;
}

function hideSideNav() {
    console.log("hideSideNav");
    // document.querySelector("#nav-side").style.display = "none";
    document.querySelector("#nav-side").classList.remove("navFadeOut");
    document.querySelector("#nav-side").classList.add("navFadeIn");
    // document.querySelector("#nav-button").setAttribute("data-display", "0");
    // navBarShowFlag = false;
}


function sendAdminLogEmail(contextPath) {

    let deleteSendLog = false;

    if (confirm("모든 관리자 로그를 이메일로 전송 하시겠습니까?")) {
        if (confirm("전송한 이메일을 삭제하시겠습니까?")) {
            deleteSendLog = true;
        } else {
            deleteSendLog = false;
        }
    } else {
        return;
    }

    $.ajax({
        type: "get",
        url: contextPath + "/api/v1/sendEmail",
        contentType: false,
        // processData: false,
        data: {deleteSendLog: deleteSendLog}
        ,
        success: function (result, status, statusCode) {
        },
        error: function (result, status, statusCode) {
        }
    });

}


function openInputModal(formId, url, sid) {

    $.ajax({
        type: "get",
        url: url,
        contentType: false,
        // processData: false,
        data: {sid: sid}
        ,
        success: function (result, status, statusCode) {
            document.getElementById(formId).innerHTML = result;

            document.getElementById(formId).style.display = "grid";
            document.getElementById(formId).classList.add("modalFadeIn");
            document.getElementById(formId).classList.remove("modalFadeOut");

            nginxServerString2Set();
        },
        error: function (result, status, statusCode) {
            Notify(
                'form 호출 에러',
                result.responseJSON.error_msg,
                'error'
            );
            console.log(result);
        }
    });

}

function nameInherite(selector, dist) {
    let selectValue = selector.value;
    let optionArr = selector.querySelectorAll("option");
    var name_ = "";
    for (let i = 0; i < optionArr.length; i++) {
        var option_ = optionArr[i];
        if (option_.value == selectValue) {
            name_ = option_.getAttribute("data-name");
            break;
        }
    } // for i end

    document.getElementById(dist).value = name_;
}

function closeInputModal(formId) {
    try {
        document.getElementById(formId).style.display = "none";
        document.getElementById(formId).classList.add("modalFadeOut");
        document.getElementById(formId).classList.remove("modalFadeIn");
        document.getElementById(formId).innerHTML = "";
    } catch (error) {
    }
    callList();

    try {
        nginxServerSidSet.clear();
    } catch (e) {
    }
}

function callList() {
    setTimeout(() => {
        const listLocNull = document.getElementById("listLocation") == null;
        const listLocUnde = document.getElementById("listLocation") == undefined;
        console.log("listLocNull", listLocNull, "listLocUnde", listLocUnde)
        if (listLocNull || listLocUnde) {
        } else {
            $.ajax({
                type: "get",
                url: "./list",
                contentType: false,
                processData: false,
                data: {}
                ,
                success: function (result, status, statusCode) {
                    const listLocNull = document.getElementById("listLocation") == null;
                    const listLocUnde = document.getElementById("listLocation") == undefined;
                    if (listLocNull || listLocUnde) {
                        setTimeout(() => {
                            callList();
                        }, 1000);
                    } else {
                        document.getElementById("listLocation").innerHTML = result;
                    }
                },
                error: function (result, status, statusCode) {
                    Notify(
                        'error',
                        result.responseJSON.error_msg,
                        'error'
                    );
                }
            });
        }
    }, 222);
}

function submitFormAjax(formId, modalId, URL, method, successCallBack, errorCallBack) {

    console.log(formId, modalId, URL);

    var submitFormData = new FormData(document.getElementById(formId));

    $.ajax({
        type: method,
        url: URL,
        contentType: false,
        processData: false,
        data: submitFormData
        ,
        success: function (result, status, statusCode) {
            if (successCallBack == null) {
                Notify(
                    'success',
                    formId + ' 저장 성공',
                    'success'
                );
                closeInputModal(modalId);
            } else {
                successCallBack();
            }
        },
        error: function (result, status, statusCode) {
            console.log(result);
            if (errorCallBack == null) {
                Notify(
                    'error',
                    result.responseJSON.error_msg,
                    'error'
                );
                closeInputModal(modalId);
            } else {
                errorCallBack();
            }
        }
    });
}

function submitFormAjax_NginxPolicy(formId, modalId, URL, successCallBack, errorCallBack) {

    console.log(formId, modalId, URL);

    const arr = document.getElementsByClassName("nginx-server-chkbox");
    let str = "";

    for (let i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
            if (str != "") {
                str += ",";
            }
            str += arr[i].value;
        }
    }

    document.getElementById("nginxServerSidString").value = str;

    // nginxServerSidString

    var submitFormData = new FormData(document.getElementById(formId));

    $.ajax({
        type: "post",
        url: URL,
        contentType: false,
        processData: false,
        data: submitFormData
        ,
        success: function (result, status, statusCode) {
            if (successCallBack == null) {
                Notify(
                    'success',
                    formId + ' 저장 성공',
                    'success'
                );
                closeInputModal(modalId);
            } else {
                successCallBack();
            }
        },
        error: function (result, status, statusCode) {
            console.log(result);
            if (errorCallBack == null) {
                Notify(
                    'error',
                    result.responseJSON.error_msg,
                    'error'
                );
                closeInputModal(modalId);
            } else {
                errorCallBack();
            }
        }
    });
}

function submitFormAjax_AdminSetting(formId, modalId, URL, successCallBack, errorCallBack) {

    console.log(formId, modalId, URL);

    var submitFormData = new FormData(document.getElementById(formId));

    $.ajax({
        type: "post",
        url: URL,
        contentType: false,
        processData: false,
        data: submitFormData
        ,
        success: function (result, status, statusCode) {
            if (successCallBack == null) {
                Notify(
                    'success',
                    formId + ' 저장 성공',
                    'success'
                );
                // closeInputModal(modalId);
                callList();
            } else {
                successCallBack();
            }
        },
        error: function (result, status, statusCode) {
            console.log(result);
            if (errorCallBack == null) {
                Notify(
                    'error',
                    result.responseJSON.error_msg,
                    'error'
                );
                // closeInputModal(modalId);
            } else {
                errorCallBack();
            }
        }
    });
}

function unbanIp(this_, URL) {

    var ip = this_.getAttribute("data-ip");
    var token = this_.getAttribute("data-token");
    var sid = this_.getAttribute("data-sid");
    var job = this_.getAttribute("data-job");

    $.ajax({
        type: "post",
        url: URL,
        // contentType: false,
        // processData: false,
        data: {
            sid: sid,
            ip: ip,
            token: token,
            job: job
        }
        ,
        success: function (result, status, statusCode) {
            Notify(
                'success',
                ip + ' 차단해제',
                'success'
            );
        },
        error: function (result, status, statusCode) {
            Notify(
                'error',
                result.responseJSON.error_msg,
                'error'
            );
        }
    });


}

/**
 * <PRE>
 * 이 div 내부의 checkbox 토글
 * </PRE>
 * @param this_
 * @param className1
 * @param className2
 * @param className3
 */
function toggleAdminSetting(this_, childArrays, parentArrays) {
    const checked = this_.getElementsByTagName("input")[0].value === 'true';
    console.log("checked? => " + checked + "  ==>  " + !checked);
    this_.getElementsByTagName("input")[0].value = (!checked + "");
    this_.classList.remove("toggle-false", "toggle-true");
    this_.classList.add("toggle-" + !checked);

    try {
        if (checked) {
            for (let i = 0; i < childArrays.length; i++) {
                const tagClassName = childArrays[i];
                console.log(" 바꾼다 chlidNode? => " + tagClassName);
                const ARR = document.getElementsByClassName(tagClassName);
                for (let j = 0; j < ARR.length; j++) {
                    const temp_ = ARR[j];
                    const childChecked = temp_.getElementsByTagName("input")[0].value === 'true';
                    if (childChecked) {
                        toggleAdminSetting(temp_);
                    }
                } // for j end
            } // for i end
        } // if checked end
    } catch (e) {
    }

    try {
        if (!checked) {
            for (let i = 0; i < parentArrays.length; i++) {
                const tagClassName = parentArrays[i];
                console.log(" 바꾼다 parentNode? => " + tagClassName);
                const ARR = document.getElementsByClassName(tagClassName);
                for (let j = 0; j < ARR.length; j++) {
                    const temp_ = ARR[j];
                    const childChecked = temp_.getElementsByTagName("input")[0].value === 'false';
                    if (childChecked) {
                        toggleAdminSetting(temp_);
                    }
                } // for j end
            } // for i end
        } // if checked end
    } catch (e) {
    }
} // toggleAdminSetting end


function adminSettingAjax(URL) {
    $.ajax({
        type: "post",
        url: URL,
        // contentType: false,
        // processData: false,
        data: {
        }
        ,
        success: function (result, status, statusCode) {
            Notify(
                'success',
              result,
                'success'
            );
        },
        error: function (result, status, statusCode) {
            Notify(
                'error',
                result.responseJSON.error_msg,
                'error'
            );
        }
    });
}


function Notify(title, content, type) {
    Notification({
        title: title,
        message: content,
        theme: type
    });
}


const Notification = window.createNotification({
    // options here
    // close on click
    closeOnClick: true,
    // displays close button
    displayCloseButton: false,
    // nfc-top-left
    // nfc-bottom-right
    // nfc-bottom-left
    positionClass: 'nfc-bottom-right',
    // callback
    // onclick: false,
    // timeout in milliseconds
    showDuration: 5000,
    // success, info, warning, error, and none
    // theme: 'success'
});

// Notification({
//     title: 'success',
//     message: 'Notification Message',
//     theme: 'success'
// });

// Notification({
//     title: 'info',
//     message: 'Notification Message',
//     theme: 'info'
// });

// Notification({
//     title: 'warning',
//     message: 'Notification Message',
//     theme: 'warning'
// });

// Notification({
//     title: 'error',
//     message: 'Notification Message',
//     theme: 'error'
// });


function deleteListItem(sid, URL) {

    if (confirm("경고 - delete???")) {

    } else {
        return;
    }

    $.ajax({
        type: "delete",
        url: URL,
        // contentType: false,
        // processData: false,
        data: {
            sid: sid
        }
        ,
        success: function (result, status, statusCode) {
            callList();
        },
        error: function (result, status, statusCode) {
            Notify(
                '삭제 실패',
                result.responseJSON.error_msg,
                'error'
            );
        }
    });
}


var nginxServerSidSet = new Set();

/**
 *
 */
function insertNewNginxServer() {
    var e = document.getElementById("nginxServerSelector").value;

    if (e > -1) {
        nginxServerSidSet.add(e);
        nginxServerSidSet.add(e.toString());
    }
    // console.log(nginxServerSidSet);
    nginxServerSet2String();
}

/**
 *
 */
function deleteNginxServer(e) {
    nginxServerSidSet.delete(e);
    nginxServerSidSet.delete(e.toString());

    // console.log(nginxServerSidSet);

    nginxServerSet2String();
}

/**
 * set() 값 > 문자열로 변환
 */
function nginxServerSet2String() {

    var newVal = '';
    nginxServerSidSet.forEach(function (value) {

        if (value.trim() != '') {
            newVal += value + ", ";
        }
    });
    newVal += ",";
    newVal = newVal.replace(", ,", "");
    document.getElementById("nginxServerSidString").value = newVal;
    if (newVal.trim() == ",") {
        document.getElementById("nginxServerSidString").value = '';
    }

    nginxServerString2Set();
}

/**
 * 문자열값 > set() 세팅
 */
function nginxServerString2Set() {
    console.log("===========================")
    try {
        var idCartTypeValueArr = document.getElementById("nginxServerSidString").value.split(",");
        for (let i = 0; i < idCartTypeValueArr.length; i++) {
            if (idCartTypeValueArr[i].trim() != '') {
                nginxServerSidSet.add(idCartTypeValueArr[i].trim());
            }
        }
        nginxServerSidSet2Label();
    } catch (error) {
        console.log(error);
    }
}

/**
 * set에서 라벨 만들어주는 function
 */
function nginxServerSidSet2Label() {

    var htmlLocation = document.getElementById("nginx-server-selected-location");

    var optionArr = document.getElementById("nginxServerSelector").querySelectorAll("option");

    htmlLocation.innerText = '';
    var htmlStr = '';
    nginxServerSidSet.forEach(function (value) {

        var sid_ = "";
        var name_ = "";
        var domain_ = "";
        var ip_ = "";
        var port_ = "";
        var favicon_ = "";
        var log_ = "";
        var https_ = "";

        for (let i = 0; i < optionArr.length; i++) {
            var option_ = optionArr[i];
            if (option_.value == value) {
                sid_ = option_.value;
                name_ = option_.getAttribute("data-name");
                domain_ = option_.getAttribute("data-domain");
                ip_ = option_.getAttribute("data-ip");
                port_ = option_.getAttribute("data-port");
                favicon_ = option_.getAttribute("data-favicon");
                log_ = option_.getAttribute("data-log");
                https_ = option_.getAttribute("data-https");
                break;
            }
        } // for i end

        htmlStr += '<div class="nginx-policy-form-list-row">';
        htmlStr += '<div class="list-row-item">';

        htmlStr += '<div class="">';
        htmlStr += name_;
        htmlStr += '</div>';
        htmlStr += '<div class="">';
        htmlStr += domain_;
        htmlStr += '</div>';
        htmlStr += '<div class="">';
        if (https_ == 'true') {
            htmlStr += "https://" + ip_ + ":" + port_;
        } else {
            htmlStr += "http://" + ip_ + ":" + port_;
        }
        htmlStr += '</div>';
        htmlStr += '<div class="">';
        if (log_ == 'true') {
            htmlStr += "로그분리  " + favicon_;
        } else {
            htmlStr += "로그통합  " + favicon_;
        }
        htmlStr += '</div>';

        htmlStr += '</div>';
        htmlStr += '<div class="list_row_del" onclick="deleteNginxServer(' + sid_ + ')">';
        htmlStr += '<i class="far fa-trash-alt"></i>';
        htmlStr += '</div>';
        htmlStr += '</div>';
    });

    htmlLocation.innerHTML = htmlStr;
}


function adjustNginxSetting(formId, URL) {

    var submitFormData = new FormData(document.getElementById(formId));

    $.ajax({
        type: "PATCH",
        url: URL,
        contentType: false,
        processData: false,
        data: submitFormData
        ,
        success: function (result, status, statusCode) {
            Notify(
                '적용 성공',
                'success'
            );
            callList();
        },
        error: function (result, status, statusCode) {
            Notify(
                '적용 실패',
                result.responseJSON.error_msg,
                'error'
            );
        }
    });
}

function NO_ACTION() {

}


function notiFormTypeSelect() {

    console.log("aaaaaa");

    try {
        var column1Div = document.getElementById("notiColumn1");
        var column2Div = document.getElementById("notiColumn2");
        var notiTypeSelect = document.getElementById("notiTypeSelect");

        var column1DivTitleDiv = column1Div.getElementsByTagName("div")[0];
        var column2DivTitleDiv = column2Div.getElementsByTagName("div")[0];

        switch (notiTypeSelect.value) {

            case "NATEON":
                column1Div.style.display = "block";
                column2Div.style.display = "none";

                column1DivTitleDiv.innerText = "팀룸 API URL";
                column2DivTitleDiv.innerText = "";
                break;

            case "SLACK":
                column1Div.style.display = "block";
                column2Div.style.display = "block";

                column1DivTitleDiv.innerText = "token";
                column2DivTitleDiv.innerText = "room id";
                break;

            default:
                column1Div.style.display = "none";
                column2Div.style.display = "none";
                break;
        }
    } catch (e) {
        console.log(e);
    }
} // notiFormTypeSelect end