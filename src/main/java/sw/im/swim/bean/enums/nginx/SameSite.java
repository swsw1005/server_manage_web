package sw.im.swim.bean.enums.nginx;

public enum SameSite {
    none("서드 파티 쿠키도 전송됩니다")
    ,
    lax("대체로 서드 파티 쿠키는 전송되지 않지만, 몇 가지 예외적인 요청에는 전송됩니다.")
    ,
    strict("서드 파티 쿠키는 전송되지 않고, 퍼스트 파티 쿠키만 전송됩니다.")
    ;

    public final String description;

    SameSite(String description) {
        this.description = description;
    }
}
