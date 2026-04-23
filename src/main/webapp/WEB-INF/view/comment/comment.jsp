<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style type="text/css">
table.comment {
	font-size: 10pt;
	margin-bottom: 15px;
}



.pop-layer .pop-container {
  padding: 20px 25px;
}

.pop-layer p.ctxt {
  color: #666;
  line-height: 25px;
}

.pop-layer .btn-r {
  width: 100%;
  margin: 10px 0 20px;
  padding-top: 10px;
  border-top: 1px solid #DDD;
  text-align: right;
}

.pop-layer {
  display: none;
  width: 410px;
  height: auto;
  
  background-color: #fff;
  border: 5px solid #3571B5;
  z-index: 10;
  
  margin-top: -50px; margin-left: -50px; position: fixed; top: 40%; left: 40%;
  
  box-shadow: 0 0 0 9999px rgba(0,0,0,0.5);
}

a.btn-layerClose {
  display: inline-block;
  height: 25px;
  padding: 0 14px 0;
  border: 1px solid #304a8a;
  background-color: #3f5a9d;
  font-size: 13px;
  color: #fff;
  line-height: 25px;
}

a.btn-layerClose:hover {
  border: 1px solid #091940;
  background-color: #1f326a;
  color: #fff;
}


#content1 {
  position: absolute; 
  left: 0; 
  right: 0; 
  margin-left: auto; 
  margin-right: auto; 
  margin-top: auto; 
  margin-bottom: auto; 
  width: 100px; /* Need a specific value to work */
}

</style>

<script type="text/javascript">
function checkLength(e, val) {
	var maxLength = 300;
	if (e.keyCode != 8 && val.length >= maxLength) {  // 8 : 백스페이스키
		alert("<spring:message code="comment.max200" arguments="300" text="최대 300 자까지 입력 가능합니다." />");
		e.preventDefault();
		return false;
	}
}

function inputComment() {
	//document.commentInputFrm.submit();
	
	if ($("#userName").val().trim() == "") {
		alert("<spring:message code="comment.inputName" text="이름을 입력해주시기 바랍니다." />");
		$("#userName").focus();
		return false;
	}

	if ($("#userPassword").val().trim() == "") {
		alert("<spring:message code="comment.inputPassword" text="비밀번호를 입력해주시기 바랍니다." />");
		$("#userPassword").focus();
		return false;
	}

	if ($("#commentInput").val().trim() == "") {
		alert("<spring:message code="comment.inputContent" text="내용을 입력해주시기 바랍니다." />");
		$("#commentInput").focus();
		return false;
	}
	
	$.ajax({
		type: "POST",
		url: "/inputComment.do",
		data: $( "#commentInputFrm" ).serialize(),
		success: function(data) {
				alert("<spring:message code="comment.inputed" text="입력되었습니다." />");
				location.reload();
			},
		error: function(data) {
				console.log(data);
				alert("<spring:message code="comment.errorOccurred" text="에러가 발생했습니다." />");
			}//,
		//dataType: dataType
	});
}

function setDefault() {
	$("input[id^='update1Button_']").show();
	$("input[id^='update2Button_']").hide();
	$("td[id^='content1_']").show();
	$("td[id^='content2_']").hide();
}

function toggleUpdateButton(commentIdx) {

	setDefault();
	
	$("#update1Button_" + commentIdx).hide();
	$("#update2Button_" + commentIdx).show();
	$("#content1_" + commentIdx).hide();
	$("#content2_" + commentIdx).show();
}

var valueCommentIdx = "";
var valueAction = "";
var valueUserPassword = "";

function checkPassword(commentIdx, action) {
	
	valueCommentIdx = commentIdx;
	valueAction = action;

	$("#layer1").show();
	
	$("#userPasswordPopup").focus();
}

function submitPasswordPopup() {
	valueUserPassword = $("#userPasswordPopup").val();
	if (valueUserPassword.trim() == "") {
		alert("<spring:message code="comment.inputPassword" text="비밀번호를 입력해주시기 바랍니다." />");
	} else {
		$("#layer1").hide();
		submitPassword(valueCommentIdx, valueAction);
	}
}

function closePasswordPopup() {
	$("#layer1").hide();;
}

function submitPassword(commentIdx, action) {
		
	setDefault();

	//var userPassword = prompt("비밀번호를 입력해주시기 바랍니다.");
	var userPassword = valueUserPassword;

	if (userPassword == null || userPassword == "") {
		return;
	}

	var url = "";
	var msg = "";
	var data = null;
	
	if (action == "update") {
		url = "/updateComment.do";
		msg = "<spring:message code="comment.modified" text="수정되었습니다." />";
		
	} else if (action == "delete") {
		url = "/deleteComment.do";
		msg = "<spring:message code="comment.deleted" text="삭제되었습니다." />";
	}
	
	
	$.ajax({
		type: "POST",
		url: "/checkCommentPassword.do",
		data: { userPassword : userPassword, commentIdx : commentIdx, content : $("#content2Text").val(), action : action },
		success: function(data) {
				//alert("checkCommentPassword data : " + data);
				
				if (data.checkCount == 0) {
					
					alert("<spring:message code="comment.passwordNotMatch" text="비밀번호가 일치하지 않습니다.\n다시 입력해주시기 바랍니다." />");
					return false;
				
				} else {
					alert(msg);
					location.reload();
				}
			},
		error: function(data) {
				console.log(data);
				alert("<spring:message code="comment.errorOccurred" text="에러가 발생했습니다." />");
			}//,
		//dataType: dataType
	});
}



$('.btn-example').click(function(){
    var $href = $(this).attr('href');
    layer_popup($href);
});

function layer_popup() {

	
	
    var $el = $("#layer1");        //레이어의 id를 $el 변수에 저장
    //var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

    //isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

    $("#layer1").show();
    
    //alert("1");
    
    var $elWidth = ~~($el.outerWidth()),
        $elHeight = ~~($el.outerHeight()),
        docWidth = $(document).width(),
        docHeight = $(document).height();

    // 화면의 중앙에 레이어를 띄운다.
    if ($elHeight < docHeight || $elWidth < docWidth) {
        $el.css({
            marginTop: -$elHeight /2,
            marginLeft: -$elWidth/2
        })
    } else {
        $el.css({top: 0, left: 0});
    }

    $el.find('a.btn-layerClose').click(function(){
        //isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
        return false;
    });

    $('.layer .dimBg').click(function(){
        $('.dim-layer').fadeOut();
        return false;
    });

}
</script>
</head>


<div style="padding: 10px;">

<h4><spring:message code="comment.comment" text="댓글" /></h4>

<form method="post" action="/inputComment.do" name="commentInputFrm" id="commentInputFrm">
<input type="hidden" name="boardArticleIdx" value="${param.articleNo }" />

<table class="comment">
	<tr>
		<td><spring:message code="comment.name" text="이름" /></td>
		<td>
		<input type="text" name="userName" id="userName" maxlength="60" class="commentInput" />
		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  
		<spring:message code="comment.password" text="비밀번호" />
		<input type="password" name="userPassword" id="userPassword" class="commentInput" />
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><spring:message code="comment.content" text="내용" /></td>
		<td>
			<textarea name="content" class="commentInput" id="commentInput" style="width: 650px;" rows="3" onkeydown="checkLength(event, this.value);"></textarea>
		</td>
		<td>
			<input type="button" value='<spring:message code="comment.input" text="입력" />' onclick="inputComment();" class="button" />
		</td>
	</tr>
</table>
</form>

<c:forEach var="item" items="${commentList }" varStatus="status">
	<table class="comment">
		<tr>
			<td width="650px" id="content1_${item.commentIdx }"><pre>${item.content }</pre></td>
			<td width="650px" id="content2_${item.commentIdx }" style="display: none;"><textarea style="width: 100%;" id="content2Text" rows="5">${item.content }</textarea></td>
			<td align="center" width="125px">
			<spring:message code="comment.writer" text="작성자" /> -
			${item.userName }<br style="line-height: -5px;"/>
			${item.dateCreated }<br style="line-height: 200%;"/>
			<input type="button" value="<spring:message code="comment.modify" text="수정" />" onclick="toggleUpdateButton('${item.commentIdx }');" id="update1Button_${item.commentIdx }" class="button2" />
			<input type="button" value="<spring:message code="comment.apply" text="반영" />" onclick="checkPassword('${item.commentIdx }', 'update');" id="update2Button_${item.commentIdx }" style="display: none;" class="button2" />
			<input type="button" value="<spring:message code="comment.delete" text="삭제" />" onclick="checkPassword('${item.commentIdx }', 'delete');" class="button2" /> 
			<!-- input type="button" value="답글" /-->
			</td>
		</tr>
	</table>
</c:forEach>

<div id="layer1" class="pop-layer">
    <div class="pop-container">
        <div class="pop-conts">
            <!--content //-->
            <p class="ctxt mb20"><spring:message code="comment.checkPassword" text="비밀번호를 확인합니다." /><br/><br/>
				<spring:message code="comment.enterPassword" text="댓글 등록시 입력한 비밀번호를 입력해주시기 바랍니다." />
				<br/><br/>
				<form name="temp1" method="post" action="/temp1">
                <input type="password" name="userPasswordPopup" id="userPasswordPopup" /><br/>
                </form>
            </p>

            <div class="btn-r">
            	<a href="javascript:submitPasswordPopup();" class="btn-layerClose"><spring:message code="comment.confirm" text="확인" /></a> &nbsp;
                <a href="javascript:closePasswordPopup();" class="btn-layerClose"><spring:message code="comment.cancel" text="취소" /></a>
            </div>
            <!--// content-->
        </div>
    </div>
</div>

<!-- input type="button" value="일반 팝업레이어" onclick="layer_popup();" / -->


</div>
