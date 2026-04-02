package cn.edu.cqjtu.cs.credit.common.entity.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.edu.cqjtu.cs.credit.common.entity.BaseEntity;
import cn.edu.cqjtu.cs.credit.common.entity.po.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends BaseEntity implements UserDetails {
    private SysUser sysUser;
    private List<String> permissions;

    @Override
    @JsonIgnore // 关键点：让 Jackson 彻底无视这个方法对应的属性
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissions == null)
            return null;
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore // 密码也不建议存序列化结果，直接用 sysUser 里的就行
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    @JsonIgnore // 同理
    public String getUsername() {
        return sysUser.getUsername();
    }

    // 下面这些 Boolean 方法建议全部加上 @JsonIgnore，防止产生冗余字段
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
