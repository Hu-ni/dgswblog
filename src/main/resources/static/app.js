$(() => Init());

let userId = 1;

let Init = function() {
    getViewPost("user", 1);
    getUserInfo(userId);
}

function writePost(){
    let input = `<div>
                    <div><input id="post-title" placeholder="제목" type="text"></div>
                    <div><textarea cols="100" rows="50" id="post-content" placeholder="내용"/></div>
                    <div>
                        <button id="post-ok-button" onclick="addNewPost()">확인</button>
                        <button id="post-cancel-button" onclick="Init()">취소</button>
                    </div>
                </div>`
    $(".content").html(input);
}

async function addNewPost(){
    let post = {
        userId: userId,
        content: $("#post-content").val(),
        title: $("#post-title").val()
    };
    let response = await $.ajax({
        type: "POST",
        url: "writePost",
        contentType: "application/json",
        data: JSON.stringify(post)
    });
    console.log(response);
    Init();
}

async function getUserInfo(id){
    let response = await $.get("/findUser/" + id);
    console.log(response);
    $("#user-details-account").html(response.data.account);
    $("#user-details-email").html(response.data.email);
    $("#user-details-created").html(response.data.created);
    getListPost(id);
}

async function getListPost(id){
    let response = await $.get("/postListByUserId/" + id);
    console.log(response);
    let list = `<ul id="post-list">`;
    for(let i = 0; i< response.data.length; i++){
        list += `<li id="${response.data[i].id}" onclick="getViewPost('post',${response.data[i].id})">
                ${response.data[i].title}
                </li>`;
    }
    $("#show-post-list").html(`<div id="post-list-container">최신글</div>` + list);
    $("#user-details-posts").html(response.data.length);

}

async function getViewPost(type, id) {
    let response = null;
    if(type === "user")
        response = await $.get("/findPostByUserId/"+id);
    else if(type === "post")
        response = await $.get("/findPostByPostId/"+id);
    console.log(response);
    let post = `<div id="post${response.data.id}">
                        <div id="post${response.data.id}Title">${response.data.title}</div>
                        <div id="post-writer-info">
                            <span>${response.data.name}</span>
                            <span>${response.data.created}</span>
                        </div>
                        <div id="post${response.data.id}Content">${response.data.content}</div>
                        <div>
                            <button id="show-before-button" 
                                onclick="getViewPost('post',${response.data.id - 1})">이전 글</button>
                            <button id="post-edit-button" onclick="edit(this, ${response.data.id})">수정</button>
                            <button id="post-delete-button" onclick="remove(this,${response.data.id})">삭제</button>
                            <button id="show-after-button"
                                onclick="getViewPost('post',${response.data.id + 1})">다음 글</button>
                        </div>
                    </div>`;
    $(".content").html(post);
}

let content;
let title;
async function edit(btn, id){
    if($(btn).text() === "수정"){
        content = $(`#post${id}Content`).text();
        title = $(`#post${id}Title`).text();
        let input_content = `<input id='input_content' value="${content}"/>`;
        let input_title = `<input id="input_title" value="${title}"/>`

        $(`#post${id}Content`).html(input_content);
        $(`#post${id}Title`).html(input_title);

        $(btn).text("확인");
        $(btn).next().text("취소");

    }else{
        console.log($(`#input_content`).val());
        let post = {
            content: $(`#input_content`).val(),
            title: $(`#input_title`).val()
        };

        let response = await $.ajax({
            type: "PUT",
            url: `/updatePost/${id}`,
            contentType: "application/json",
            data: JSON.stringify(post)
        });

        getViewPost("post",id);
        getListPost(userId);

        $(btn).text("수정");
        $(btn).next().text("삭제");
    }
}

async function remove(btn, id){
    if($(btn).text() === "삭제"){
        try{
            let response = await $.ajax({
                type: "DELETE",
                url: `/deletePost/${id}`
            });
            console.log(response);
            if(response.data === true) Init();
            else alert("삭제 실패");
        }catch(err){
            console.log(err);
        }
    }else{
        $(`#post${id}Content`).text(content);
        $(`#post${id}Title`).text(title);


        $(btn).text("삭제");
        $(btn).prev().text("수정");
    }
}