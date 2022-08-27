package sw.im.swim.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <PRE>
 * 관리자 설정값 전달, 보관 객체
 * |   DB  table         DTO
 * |   key   value  =>  String, int , boolean
 * ** String을 제외하고 원시타입만 사용하도록 한다.
 * </PRE>
 */
@Data
public class NginxSettings implements Serializable {

}
