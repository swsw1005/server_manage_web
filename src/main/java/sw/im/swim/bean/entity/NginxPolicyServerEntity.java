package sw.im.swim.bean.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "nginx_policy_server_join")
@Table(name = "nginx_policy_server_join")
public class NginxPolicyServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", insertable = false, updatable = false)
    protected Long sid;

    @ManyToOne
    @JoinColumn(name = "nginx_policy_info_sid")
    private NginxPolicyEntity nginxPolicyEntity;

    @ManyToOne
    @JoinColumn(name = "nginx_server_info_sid")
    private NginxServerEntity nginxServerEntity;

}
