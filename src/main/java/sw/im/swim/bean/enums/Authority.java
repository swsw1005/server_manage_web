package sw.im.swim.bean.enums;

public enum Authority {

    /**
     * 최초 가입시 wait 상태, 이메일 인증 통해서 User가 되어야 이용 가능
     */
    ROLE_WAIT,

    /**
     * 주문 가능
     */
    ROLE_USER,

    /**
     * 주문, 방 생성, 메뉴정보 수정 가능
     */
    ROLE_MANAGER,

    /**
     * 관리자 로그 열람 가능
     */
    ROLE_ADMIN
}