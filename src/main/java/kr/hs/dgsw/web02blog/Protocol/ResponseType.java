package kr.hs.dgsw.web02blog.Protocol;

public enum ResponseType {
    FAIL                (0, "명령을 실행하지 못했습니다."),

    USER_DELETE         (101, "사용자를 제거했습니다."),
    USER_ADD            (102, "사용자를 추가했습니다."),
    USER_UPDATE         (103, "ID [%d]의 사용자를 변경했습니다."),
    USER_GET            (104, "[%s]  님의 정보를 조회했습니다."),
    USER_GET_ALL        (105, "모든 사용자를 조회했습니다."),

    POST_GET            (201, "게시글을 조회했습니다."),
    POST_ADD            (202, "게시글을 추가했습니다."),
    POST_UPDATE         (203, "게시글을 변경했습니다."),
    POST_DELETE         (204, "게시글을 제거했습니다."),
    POST_GET_ALL        (205,"모든 게시글을 조회했습니다"),

    ATTACHMENT_STORED   (301, "파일을 가져왔습니다."),
    ;

    final private int code;
    final private String desc;

    ResponseType(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int code() {return this.code;}
    public String desc() {return this.desc;}
}
